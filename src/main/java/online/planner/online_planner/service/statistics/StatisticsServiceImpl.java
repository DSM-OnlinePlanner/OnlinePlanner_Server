package online.planner.online_planner.service.statistics;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.planner.repository.PlannerRepository;
import online.planner.online_planner.entity.routine.repository.RoutineRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.error.exceptions.UserNotFoundException;
import online.planner.online_planner.payload.response.PointResponse;
import online.planner.online_planner.payload.response.StatisticsResponse;
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
                .countByEmailAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(
                        user.getEmail(),
                        weekStart,
                        weekEnd
                );
        int maxRoutineWeek = routineRepository
                .countByEmailAndIsSucceedAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(
                        user.getEmail(),
                        true,
                        weekStart,
                        weekEnd
                );
        int succeedPlannerWeek = plannerRepository
                .countByEmailAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(
                        user.getEmail(),
                        weekStart,
                        weekEnd
                );
        int succeedRoutineWeek = routineRepository
                .countByEmailAndIsSucceedAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(
                        user.getEmail(),
                        true,
                        weekStart,
                        weekEnd
                );

        int maxPlannerMonth = plannerRepository
                .countByEmailAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(
                        user.getEmail(),
                        monthStart,
                        monthEnd
                );
        int maxRoutineMonth = routineRepository
                .countByEmailAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(
                        user.getEmail(),
                        monthStart,
                        monthEnd
                );
        int succeedPlannerMonth = plannerRepository
                .countByEmailAndIsSuccessAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(
                        user.getEmail(),
                        true,
                        monthStart,
                        monthEnd
                );
        int succeedRoutineMonth = routineRepository
                .countByEmailAndIsSucceedAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(
                        user.getEmail(),
                        true,
                        monthStart,
                        monthEnd
                );

        LocalDate sevenDaysAgo = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(7);
        List<PointResponse> pointResponses = new ArrayList<>();

        for(int i = 0; i < 7; i++) {
            int succeedPlanner = plannerRepository
                    .countByEmailAndIsSuccessAndWriteAt(user.getEmail(), true, sevenDaysAgo);
            int succeedRoutine = routineRepository
                    .countByEmailAndIsSucceedAndWriteAt(user.getEmail(), true, sevenDaysAgo);

            pointResponses.add(
                    PointResponse.builder()
                            .date(sevenDaysAgo.getDayOfMonth())
                            .succeedNum(succeedPlanner + succeedRoutine)
                            .build()
            );

            sevenDaysAgo.plusDays(1);
        }

        return StatisticsResponse.builder()
                .maxWeek(maxPlannerWeek + maxRoutineWeek)
                .weekSucceed(succeedPlannerWeek + succeedRoutineWeek)
                .maxMonth(maxPlannerMonth + maxRoutineMonth)
                .monthSucceed(succeedPlannerMonth + succeedRoutineMonth)
                .pointResponses(pointResponses)
                .build();
    }
}
