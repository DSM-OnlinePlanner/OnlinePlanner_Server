package online.planner.online_planner.service.planner;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.planner.Planner;
import online.planner.online_planner.entity.planner.repository.PlannerRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.entity.user_level.UserLevel;
import online.planner.online_planner.entity.user_level.repository.UserLevelRepository;
import online.planner.online_planner.error.exceptions.*;
import online.planner.online_planner.payload.request.*;
import online.planner.online_planner.payload.response.PageResponse;
import online.planner.online_planner.payload.response.PlannerResponse;
import online.planner.online_planner.payload.response.SearchPlannerResponse;
import online.planner.online_planner.util.AchieveUtil;
import online.planner.online_planner.util.JwtProvider;
import online.planner.online_planner.util.NotNull;
import online.planner.online_planner.util.UserLevelUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService{

    private final UserRepository userRepository;
    private final PlannerRepository plannerRepository;
    private final UserLevelRepository userLevelRepository;

    private final JwtProvider jwtProvider;
    private final UserLevelUtil userLevelUtil;
    private final NotNull notNull;
    private final AchieveUtil achieveUtil;

    public static final Integer MAX_PLANNER_PAGE = 40;

    @Override
    public void postPlanner(String token,PlannerRequest plannerRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(UserLevelNotFoundException::new);

        plannerRepository.save(
                Planner.builder()
                        .email(user.getEmail())
                        .title(plannerRequest.getTitle())
                        .content(plannerRequest.getContent())
                        .endDate(plannerRequest.getEndDate())
                        .startDate(plannerRequest.getStartDate())
                        .startTime(plannerRequest.getStartTime())
                        .endTime(plannerRequest.getEndTime())
                        .isSuccess(false)
                        .isFailed(false)
                        .isPushed(false)
                        .priority(plannerRequest.getPriority())
                        .want(plannerRequest.getWant())
                        .expType(ExpType.PLANNER)
                        .isPushed(plannerRequest.isPushed())
                        .writeAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                        .build()
        );

        achieveUtil.achievePlannerNum(
                plannerRepository.countByEmail(user.getEmail()),
                userLevel,
                true,
                false
        );
    }

    @Override
    public List<PlannerResponse> readPlanner(String token, LocalDate date, Integer pageNum) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Page<PlannerResponse> planners = plannerRepository
                .findAllByEmailAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateAsc(
                        user.getEmail(),
                        date,
                        date,
                        PageRequest.of(pageNum, MAX_PLANNER_PAGE)
                );

        return planners.toList();
    }

    @Override
    public List<PlannerResponse> mainPlanner(String token, LocalDate date) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Page<PlannerResponse> planners = plannerRepository
                .findAllByEmailAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateAsc(
                        user.getEmail(),
                        date,
                        date,
                        PageRequest.of(0, 3)
                );

        return planners.toList();
    }

    @Override
    public SearchPlannerResponse searchPlanner(String token, String title) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Page<PlannerResponse> plannerResponses = plannerRepository
                .findAllByEmailAndTitleContainingOrderByStartDateAsc(
                        user.getEmail(),
                        title,
                        PageRequest.of(
                                0,
                                3
                        )
                );

        return SearchPlannerResponse.builder()
                .plannerResponses(plannerResponses.toList())
                .searchNum(plannerRepository.countByEmailAndTitleContaining(user.getEmail(), title))
                .build();
    }

    @Override
    public PageResponse getMaxPage(String token) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        int plannerNum = plannerRepository.countByEmail(user.getEmail());

        return PageResponse.builder()
                .maxPage(plannerNum == 0 ? 0 : (plannerNum / MAX_PLANNER_PAGE) == 0 ? 1 : (plannerNum / MAX_PLANNER_PAGE) + 1)
                .build();
    }

    @Override
    public void checkSuccess(String token, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Planner planner = plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(UserLevelNotFoundException::new);

        if(planner.getIsFailed())
            throw new FailedPlannerException();

        userLevelUtil.userLevelManagement(userLevel, planner.getExpType());

        achieveUtil.achievePlannerNum(
                plannerRepository.countByIsSuccessAndEmail(true, user.getEmail()),
                userLevel,
                false,
                false
        );

        plannerRepository.save(
                planner.checkSuccess()
        );
    }

    @Override
    public void updatePlannerTitleAndContent(String token, UpdateTitleAndContentRequest updateTitleAndContentRequest, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Planner planner = plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        notNull.setIfNotNull(planner::setTitle, updateTitleAndContentRequest.getTitle());
        notNull.setIfNotNull(planner::setContent, updateTitleAndContentRequest.getContent());

        plannerRepository.save(planner);
    }

    @Override
    public void updatePlannerDate(String token, UpdateDateRequest updateDateRequest, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Planner planner = plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        notNull.setIfNotNull(planner::setStartDate, updateDateRequest.getStartDate());
        notNull.setIfNotNull(planner::setEndDate, updateDateRequest.getEndDate());

        plannerRepository.save(planner);
    }

    @Override
    public void updatePlannerTime(String token, UpdateTimeRequest updateTimeRequest, Long plannerId) {
        User user =userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Planner planner = plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        notNull.setIfNotNull(planner::setStartTime, updateTimeRequest.getStartTime());
        notNull.setIfNotNull(planner::setEndTime, updateTimeRequest.getEndTime());

        plannerRepository.save(planner);
    }

    @Override
    public void updatePlannerPushed(String token, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Planner planner = plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        plannerRepository.save(planner.updatePush());
    }

    @Override
    public void updatePriority(String token, UpdatePlannerPriorityRequest updatePlannerPriorityRequest, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Planner planner = plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        plannerRepository.save(planner.updatePriority(
                updatePlannerPriorityRequest.getPriority(), updatePlannerPriorityRequest.getWant())
        );
    }

    @Override
    public void latePlanner(String token, LatePlannerRequest latePlannerRequest, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Planner planner = plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        notNull.setIfNotNull(planner::setStartDate, latePlannerRequest.getStartDate());
        notNull.setIfNotNull(planner::setEndDate, latePlannerRequest.getEndDate());
        notNull.setIfNotNull(planner::setStartTime, latePlannerRequest.getStartTime());
        notNull.setIfNotNull(planner::setEndTime, latePlannerRequest.getEndTime());

        plannerRepository.save(planner);
    }

    @Override
    public void failedPlanner(String token, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Planner planner = plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        if(planner.getIsSuccess())
            throw new SucceedRoutineException();

        plannerRepository.save(
                planner.updateFailed()
        );
    }

    @Override
    @Transactional
    public void deletePlanner(String token, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        plannerRepository.deleteByPlannerIdAndEmail(plannerId, user.getEmail());
    }
}
