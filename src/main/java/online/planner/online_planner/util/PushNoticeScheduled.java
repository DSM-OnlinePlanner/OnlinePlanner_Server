package online.planner.online_planner.util;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.device_token.repository.DeviceTokenRepository;
import online.planner.online_planner.entity.planner.Planner;
import online.planner.online_planner.entity.planner.repository.PlannerRepository;
import online.planner.online_planner.entity.routine.Routine;
import online.planner.online_planner.entity.routine.repository.RoutineRepository;
import online.planner.online_planner.entity.routine_date.RoutineWeek;
import online.planner.online_planner.entity.routine_date.repository.RoutineWeekRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PushNoticeScheduled {

    private final DeviceTokenRepository deviceTokenRepository;
    private final RoutineWeekRepository routineWeekRepository;
    private final RoutineRepository routineRepository;
    private final PlannerRepository plannerRepository;

    private final FcmUtil fcmUtil;

    @Scheduled(cron = "0 0 */1 * * *")
    public void pushPlannerNotice() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = ChronoUnit.HOURS.addTo(start, 1);

        List<Planner> pushPlanner = plannerRepository.findAllByIsPushedAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
                false,
                start.toLocalDate(),
                end.toLocalDate()
        );

        List<String> tokens = new ArrayList<>();

        for (Planner planner : pushPlanner) {
            plannerRepository.save(
                    planner.updatePush()
            );

            tokens.addAll(deviceTokenRepository.findAllDeviceTokenByEmail(planner.getEmail()));
        }

        int startHour = start.getHour();
        if(startHour > 12)
            startHour -= 12;

        int endHour = end.getHour();
        if(endHour > 12)
            endHour -= 12;

        fcmUtil.sendPushMessage(tokens,
                "[OnlinePlanner]할 일이 있습니다!",
                String.format("%d시 ~ %d시에 할 일이 있습니다!", startHour, endHour));
    }

    @Scheduled(cron = "0 0 */1 * * *")
    public void pushRoutineNotice() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = ChronoUnit.HOURS.addTo(start, 1);

        List<RoutineWeek> routineWeeks = routineWeekRepository
                .findAllByDayOfWeekAndRoutine_StartTimeGreaterThanEqualAndRoutine_EndTimeLessThanEqual(
                        dayOfWeek,
                        start.toLocalTime(),
                        end.toLocalTime()
                );

        List<String> tokens = new ArrayList<>();

        for(RoutineWeek routineWeek : routineWeeks) {
            routineRepository.save(
                    routineWeek.getRoutine().updatePushed()
            );

            tokens.addAll(deviceTokenRepository.findAllDeviceTokenByEmail(routineWeek.getRoutine().getEmail()));
        }

        int startHour = start.getHour();
        if(startHour > 12)
            startHour -= 12;

        int endHour = end.getHour();
        if(endHour > 12)
            endHour -= 12;

        fcmUtil.sendPushMessage(tokens,
                "[OnlinePlanner]루틴이 있습니다!",
                String.format("%d시 ~ %d시에 할 일이 있습니다!", startHour, endHour));
    }

    @Scheduled(cron = "0 0 0 */1 * *")
    public void initRoutineIsPushed() {
        List<Routine> routines = routineRepository.findAllByIsPushed(true);

        for(Routine routine : routines)
            routine.updatePushed();
    }
}
