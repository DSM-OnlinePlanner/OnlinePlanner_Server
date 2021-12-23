package online.planner.online_planner.util;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.device_token.repository.DeviceTokenRepository;
import online.planner.online_planner.entity.notice.Notice;
import online.planner.online_planner.entity.notice.repository.NoticeRepository;
import online.planner.online_planner.entity.planner.Planner;
import online.planner.online_planner.entity.planner.repository.PlannerRepository;
import online.planner.online_planner.entity.routine.Routine;
import online.planner.online_planner.entity.routine.repository.RoutineRepository;
import online.planner.online_planner.entity.routine_date.RoutineWeek;
import online.planner.online_planner.entity.routine_date.repository.RoutineWeekRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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
    private final NoticeRepository noticeRepository;

    private final FcmUtil fcmUtil;

    @Scheduled(cron = "0 */1 * * * *")
    public void pushPlannerNotice() {
        LocalDateTime start = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime end = ChronoUnit.HOURS.addTo(start, 1);

        List<Planner> pushPlanner = plannerRepository
                .findAllByIsPushedAndIsPushSuccessAndStartDateGreaterThanEqualAndEndDateLessThanAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(
                true,
                false,
                LocalDate.now(),
                LocalDate.now(),
                start.toLocalTime(),
                end.toLocalTime()
        );

        int startHour = start.getHour();
        if(startHour > 12)
            startHour -= 12;

        int endHour = end.getHour();
        if(endHour > 12)
            endHour -= 12;

        String notice = String.format("%d시 ~ %d시에 할 일이 있습니다!", startHour, endHour);

        List<String> tokens = new ArrayList<>();
        List<Notice> notices = new ArrayList<>();

        for (Planner planner : pushPlanner) {
            plannerRepository.save(
                    planner.updatePush()
            );

            notices.add(
                    Notice.builder()
                            .email(planner.getEmail())
                            .isSee(false)
                            .title(notice)
                            .noticeDate(LocalDate.now())
                            .noticemAt(LocalTime.now())
                            .build()
            );

            System.out.println(deviceTokenRepository.findAllDeviceTokenByEmail(planner.getEmail()));

            tokens.addAll(deviceTokenRepository.findAllDeviceTokenByEmail(planner.getEmail()));
        }

        noticeRepository.saveAll(notices);

        fcmUtil.sendPushMessage(tokens,
                "[OnlinePlanner]할 일이 있습니다!",
                notice);
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void pushRoutineNotice() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        LocalDateTime start = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime end = ChronoUnit.HOURS.addTo(start, 1);

        List<RoutineWeek> routineWeeks = routineWeekRepository
                .findAllByDayOfWeekAndRoutine_StartTimeGreaterThanEqualAndRoutine_EndTimeLessThanEqual(
                        dayOfWeek,
                        start.toLocalTime(),
                        end.toLocalTime()
                );

        List<String> tokens = new ArrayList<>();

        int startHour = start.getHour();
        if(startHour > 12)
            startHour -= 12;

        int endHour = end.getHour();
        if(endHour > 12)
            endHour -= 12;

        String notice = String.format("%d시 ~ %d시에 할 일이 있습니다!", startHour, endHour);

        List<Notice> notices = new ArrayList<>();

        for(RoutineWeek routineWeek : routineWeeks) {
            routineRepository.save(
                    routineWeek.getRoutine().updatePushed()
            );

            notices.add(
                    Notice.builder()
                            .email(routineWeek.getRoutine().getEmail())
                            .isSee(false)
                            .title(notice)
                            .noticeDate(LocalDate.now())
                            .noticemAt(LocalTime.now())
                            .build()
            );

            tokens.addAll(deviceTokenRepository.findAllDeviceTokenByEmail(routineWeek.getRoutine().getEmail()));
        }

        noticeRepository.saveAll(notices);

        if(!tokens.isEmpty()) {
            fcmUtil.sendPushMessage(tokens,
                    "[OnlinePlanner]루틴이 있습니다!",
                    notice
            );
        }
    }

    @Scheduled(cron = "0 0 0 */1 * *")
    public void initRoutineIsPushed() {
        List<Routine> routines = routineRepository.findAllByIsPushed(true);
        List<Routine> routines1 = routineRepository.findAllByIsSucceed(true);
        List<Routine> routines2 = routineRepository.findAllByIsFailed(true);

        for(Routine routine : routines)
            routine.updatePushed();

        for(Routine routine : routines1)
            routine.getIsSucceed();

        for(Routine routine : routines2)
            routine.updateFailed();
    }
}
