package online.planner.online_planner.service.statistics;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.planner.repository.PlannerRepository;
import online.planner.online_planner.entity.routine.repository.RoutineRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.error.exceptions.UserNotFoundException;
import online.planner.online_planner.payload.response.PlannerStatisticsResponse;
import online.planner.online_planner.payload.response.PointResponse;
import online.planner.online_planner.payload.response.StatisticsResponse;
import online.planner.online_planner.payload.response.WebStatisticsResponse;
import online.planner.online_planner.util.JwtProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final PlannerRepository plannerRepository;
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Override
    public StatisticsResponse getMyStatistics(String token) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(WeekFields.of(Locale.KOREA).dayOfWeek(), 1);
        LocalDate weekEnd = today.with(WeekFields.of(Locale.KOREA).dayOfWeek(), 7);
        LocalDate monthStart = YearMonth.now().atDay(1);
        LocalDate monthEnd = YearMonth.now().atEndOfMonth();

        int maxPlannerWeek = plannerRepository
                .countDistinctByEmailAndStartDateGreaterThanEqualOrEmailAndEndDateLessThanEqual(
                        user.getEmail(),
                        weekStart,
                        user.getEmail(),
                        weekEnd
                );

        int succeedPlannerWeek = plannerRepository
                .countDistinctByIsSuccessAndEmailAndStartDateGreaterThanEqualOrIsSuccessAndEmailAndEndDateLessThanEqual(
                    true,
                    user.getEmail(),
                    weekStart,
                    true,
                    user.getEmail(),
                    weekEnd
                );

        int maxPlannerMonth = plannerRepository
                .countDistinctByEmailAndStartDateGreaterThanEqualOrEmailAndEndDateLessThanEqual(
                        user.getEmail(),
                        monthStart,
                        user.getEmail(),
                        monthEnd
                );

        int succeedPlannerMonth = plannerRepository
                .countDistinctByIsSuccessAndEmailAndStartDateGreaterThanEqualOrIsSuccessAndEmailAndEndDateLessThanEqual(
                        true,
                        user.getEmail(),
                        monthStart,
                        true,
                        user.getEmail(),
                        monthEnd
                );

        LocalDate sevenDaysAgo = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(7);
        List<PointResponse> pointResponses = new ArrayList<>();

        for(int i = 0; i <= 7; i++) {
            LocalDate date = sevenDaysAgo.plusDays(i);

            int succeedPlanner = plannerRepository
                    .countByEmailAndIsSuccessAndWriteAt(user.getEmail(), true, date);

            pointResponses.add(
                    PointResponse.builder()
                            .date(date.getDayOfMonth())
                            .succeedNum(succeedPlanner)
                            .build()
            );
        }

        return StatisticsResponse.builder()
                .maxWeek(maxPlannerWeek)
                .weekSucceed(succeedPlannerWeek)
                .maxMonth(maxPlannerMonth)
                .monthSucceed(succeedPlannerMonth)
                .pointResponses(pointResponses)
                .build();
    }

    @Override
    public PlannerStatisticsResponse getStatistics(String token) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        int maxPlannerToday = plannerRepository
                .countByEmailAndWriteAt(
                        user.getEmail(),
                        LocalDate.now(ZoneId.of("Asia/Seoul"))
                );

        int succeedPlannerToday = plannerRepository
                .countByEmailAndIsSuccessAndWriteAt(
                        user.getEmail(),
                        true,
                        LocalDate.now(ZoneId.of("Asia/Seoul"))
                );

        return PlannerStatisticsResponse.builder()
                .maxPlannerToday(maxPlannerToday)
                .successPlannerToday(succeedPlannerToday)
                .build();
    }

    @Override
    public WebStatisticsResponse getWebStatics(String token) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(13);
        List<PointResponse> sevenDatesPlannerNum = new ArrayList<>();
        List<PointResponse> fourTeenDatesPlannerNum = new ArrayList<>();
        List<PointResponse> sevenDatesSuccessNum = new ArrayList<>();
        List<PointResponse> fourTeenDatesSuccessNum = new ArrayList<>();

        for(int i = 0; i < 14; i++) {
            LocalDate date = today.plusDays(i);

            int plannerNum = plannerRepository.countByEmailAndWriteAt(user.getEmail(), date);
            int successNum = plannerRepository.countByEmailAndIsSuccessAndWriteAt(user.getEmail(), true, date);
            if(i <= 6) {
                sevenDatesPlannerNum.add(
                       PointResponse.builder()
                               .date(date.getDayOfMonth())
                               .succeedNum(plannerNum)
                               .build()
               );
                sevenDatesSuccessNum.add(
                        PointResponse.builder()
                                .date(date.getDayOfMonth())
                                .succeedNum(successNum)
                                .build()
                );
            }else {
                fourTeenDatesPlannerNum.add(
                        PointResponse.builder()
                                .date(date.getDayOfMonth())
                                .succeedNum(plannerNum)
                                .build()
                );
                fourTeenDatesSuccessNum.add(
                        PointResponse.builder()
                                .date(date.getDayOfMonth())
                                .succeedNum(successNum)
                                .build()
                );
            }
        }

        return WebStatisticsResponse.builder()
                .sevenDatesPlannerNum(sevenDatesPlannerNum)
                .sevenDatesSuccessNum(sevenDatesSuccessNum)
                .fourTeenDatesPlannerNum(fourTeenDatesPlannerNum)
                .fourTeenDatesSuccessNum(fourTeenDatesSuccessNum)
                .build();
    }


}
