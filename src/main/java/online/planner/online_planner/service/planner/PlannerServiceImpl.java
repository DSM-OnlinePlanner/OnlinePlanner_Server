package online.planner.online_planner.service.planner;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.planner.Planner;
import online.planner.online_planner.entity.planner.repository.PlannerRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.entity.user_level.UserLevel;
import online.planner.online_planner.entity.user_level.repository.UserLevelRepository;
import online.planner.online_planner.error.exceptions.PlannerNotFoundException;
import online.planner.online_planner.error.exceptions.UserLevelNotFoundException;
import online.planner.online_planner.error.exceptions.UserNotFoundException;
import online.planner.online_planner.payload.request.*;
import online.planner.online_planner.payload.response.PlannerResponse;
import online.planner.online_planner.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public static final Integer MAX_PLANNER_PAGE = 10;

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
                .priority(plannerRequest.getPriority().getPriority() + plannerRequest.getWant().getWant())
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
    public List<PlannerResponse> readPlanner(String token, PlannerReadRequest plannerReadRequest, Integer pageNum) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Page<PlannerResponse> planners = plannerRepository
                .findAllByEmailAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateAsc(
                        user.getEmail(),
                        plannerReadRequest.getDate(),
                        plannerReadRequest.getDate(),
                        PageRequest.of(pageNum, MAX_PLANNER_PAGE)
                );

        return planners.toList();
    }

    @Override
    public List<PlannerResponse> mainPlanner(String token, PlannerReadRequest plannerReadRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Page<PlannerResponse> planners = plannerRepository
                .findAllByEmailAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateAsc(
                        user.getEmail(),
                        plannerReadRequest.getDate(),
                        plannerReadRequest.getDate(),
                        PageRequest.of(0, 3)
                );

        return planners.toList();
    }

    @Override
    public void checkSuccess(String token, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Planner planner = plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(UserLevelNotFoundException::new);

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
                updatePlannerPriorityRequest.getPriority().getPriority() + updatePlannerPriorityRequest.getWant().getWant())
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
    public void deletePlanner(String token, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        plannerRepository.findByPlannerIdAndEmail(plannerId, user.getEmail())
                .orElseThrow(PlannerNotFoundException::new);

        plannerRepository.deleteByPlannerIdAndEmail(plannerId, user.getEmail());
    }
}
