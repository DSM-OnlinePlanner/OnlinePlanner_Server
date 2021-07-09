package online.planner.online_planner.service.planner;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.planner.Planner;
import online.planner.online_planner.entity.planner.enums.Priority;
import online.planner.online_planner.entity.planner.enums.Want;
import online.planner.online_planner.entity.planner.repository.PlannerRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.entity.user_level.UserLevel;
import online.planner.online_planner.entity.user_level.repository.UserLevelRepository;
import online.planner.online_planner.payload.request.PlannerRequest;
import online.planner.online_planner.payload.request.UpdateDateRequest;
import online.planner.online_planner.payload.request.UpdateTimeRequest;
import online.planner.online_planner.payload.request.UpdateTitleAndContentRequest;
import online.planner.online_planner.payload.response.PlannerResponse;
import online.planner.online_planner.util.JwtProvider;
import online.planner.online_planner.util.NotNull;
import online.planner.online_planner.util.UserLevelUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService{

    private final UserRepository userRepository;
    private final PlannerRepository plannerRepository;
    private final UserLevelRepository userLevelRepository;

    private final JwtProvider jwtProvider;
    private final UserLevelUtil userLevelUtil;

    public static final Integer MAX_PLANNER_PAGE = 10;

    private final NotNull notNull;

    //convert enum by Integer
    private Want setWant(Planner planner) {
        Integer wantNum = planner.getPriority() % 5;
        if(wantNum.equals(0))
            wantNum = 5;

        Integer nWant = wantNum;

        return Stream.of(Want.values())
                .filter(want1 -> want1.getWant().equals(nWant))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private Priority setPriority(Planner planner) {
        Integer wantNum = planner.getPriority() % 5;
        if(wantNum.equals(0))
            wantNum = 5;

        Integer pri = planner.getPriority() - wantNum;

        return Stream.of(Priority.values())
                .filter(priority1 -> priority1.getPriority().equals(pri))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void postPlanner(String token,PlannerRequest plannerRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        plannerRepository.save(
                Planner.builder()
                        .email(user.getEmail())
                        .title(plannerRequest.getTitle())
                        .content(plannerRequest.getContent())
                        .endDate(plannerRequest.getEndDate())
                        .startDate(plannerRequest.getStartDate())
                        .startTime(plannerRequest.getStartTime())
                        .endTime(plannerRequest.getEndTime())
                        .isSuccess(true)
                        .priority(plannerRequest.getPriority().getPriority() + plannerRequest.getWant().getWant())
                        .expType(ExpType.PLANNER)
                        .build()
        );
    }

    @Override
    public List<PlannerResponse> readPlanner(String token, Integer pageNum) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Page<Planner> planners = plannerRepository
                .findAllByEmailAndStartDateAfterAndEndDateBeforeOrderByStartTimeAsc(
                        user.getEmail(),
                        LocalDate.now(),
                        LocalDate.now(),
                        PageRequest.of(pageNum, MAX_PLANNER_PAGE)
                );
        List<PlannerResponse> responses = new ArrayList<>();

        for(Planner planner : planners) {
            PlannerResponse plannerResponse = PlannerResponse.builder()
                    .plannerId(planner.getPlannerId())
                    .title(planner.getTitle())
                    .content(planner.getContent())
                    .startTime(planner.getStartTime())
                    .endTime(planner.getEndTime())
                    .startDate(planner.getStartDate())
                    .endDate(planner.getEndDate())
                    .isSuccess(planner.getIsSuccess())
                    .priority(setPriority(planner))
                    .want(setWant(planner))
                    .build();

            responses.add(plannerResponse);
        }
        return responses;
    }

    @Override
    public List<PlannerResponse> mainPlanner(String token) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Page<Planner> planners = plannerRepository
                .findAllByEmailAndStartDateAfterAndEndDateBeforeOrderByStartTimeAsc(
                        user.getEmail(),
                        LocalDate.now(),
                        LocalDate.now(),
                        PageRequest.of(1, 3)
                );
        List<PlannerResponse> plannerResponses = new ArrayList<>();

        for(Planner planner : planners) {
            PlannerResponse plannerResponse = PlannerResponse.builder()
                    .plannerId(planner.getPlannerId())
                    .title(planner.getTitle())
                    .content(planner.getContent())
                    .want(setWant(planner))
                    .priority(setPriority(planner))
                    .startDate(planner.getStartDate())
                    .endDate(planner.getEndDate())
                    .startTime(planner.getStartTime())
                    .endTime(planner.getEndTime())
                    .isSuccess(planner.getIsSuccess())
                    .build();
            plannerResponses.add(plannerResponse);
        }

        return plannerResponses;
    }

    @Override
    public void checkSuccess(String token, Long plannerId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Planner planner = plannerRepository.findByPlannerId(plannerId)
                .orElseThrow(RuntimeException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(RuntimeException::new);

        userLevelUtil.userLevelManagement(userLevel, planner.getExpType());
    }

    @Override
    public void updatePlannerTitleAndContent(String token, UpdateTitleAndContentRequest updateTitleAndContentRequest, Long plannerId) {
        userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Planner planner = plannerRepository.findByPlannerId(plannerId)
                .orElseThrow(RuntimeException::new);

        notNull.setIfNotNull(planner::setTitle, updateTitleAndContentRequest.getTitle());
        notNull.setIfNotNull(planner::setContent, updateTitleAndContentRequest.getContent());

        plannerRepository.save(planner);
    }

    @Override
    public void updatePlannerDate(String token, UpdateDateRequest updateDateRequest, Long plannerId) {
        userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Planner planner = plannerRepository.findByPlannerId(plannerId)
                .orElseThrow(RuntimeException::new);

        notNull.setIfNotNull(planner::setStartDate, updateDateRequest.getStartDate());
        notNull.setIfNotNull(planner::setEndDate, updateDateRequest.getEndDate());

        plannerRepository.save(planner);
    }

    @Override
    public void updatePlannerTime(String token, UpdateTimeRequest updateTimeRequest, Long plannerId) {
        userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Planner planner = plannerRepository.findByPlannerId(plannerId)
                .orElseThrow(RuntimeException::new);

        notNull.setIfNotNull(planner::setStartTime, updateTimeRequest.getStartTime());
        notNull.setIfNotNull(planner::setEndTime, updateTimeRequest.getEndTime());

        plannerRepository.save(planner);
    }

    @Override
    public void deletePlanner(String token, Long plannerId) {
        userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        plannerRepository.findByPlannerId(plannerId)
                .orElseThrow(RuntimeException::new);

        plannerRepository.deleteByPlannerId(plannerId);
    }


}
