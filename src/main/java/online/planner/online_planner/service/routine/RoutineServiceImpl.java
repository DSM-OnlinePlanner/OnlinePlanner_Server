package online.planner.online_planner.service.routine;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.achivement.repository.AchievementRepository;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.routine.Routine;
import online.planner.online_planner.entity.routine.repository.RoutineRepository;
import online.planner.online_planner.entity.routine_date.RoutineWeek;
import online.planner.online_planner.entity.routine_date.enums.Weeks;
import online.planner.online_planner.entity.routine_date.repository.RoutineWeekRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.entity.user_level.UserLevel;
import online.planner.online_planner.entity.user_level.repository.UserLevelRepository;
import online.planner.online_planner.payload.request.*;
import online.planner.online_planner.payload.response.RoutineResponse;
import online.planner.online_planner.util.AchieveUtil;
import online.planner.online_planner.util.JwtProvider;
import online.planner.online_planner.util.NotNull;
import online.planner.online_planner.util.UserLevelUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService{

    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;
    private final RoutineWeekRepository routineWeekRepository;
    private final UserLevelRepository userLevelRepository;
    private final AchievementRepository achievementRepository;

    private final JwtProvider jwtProvider;
    private final UserLevelUtil userLevelUtil;
    private final NotNull notNull;
    private final AchieveUtil achieveUtil;

    private final int PAGE_NUM = 10;
    private final int MAIN_PAGE_NUM = 3;

    private List<String> setRoutineWeeks(Long routineId) {
        List<String> weeks = new ArrayList<>();
        List<RoutineWeek> routineWeeks = routineWeekRepository.findAllByRoutineId(routineId);

        for(RoutineWeek routineWeek : routineWeeks) {
            weeks.add(
                    Stream.of(Weeks.values())
                    .filter(week -> week.getDayOfWeek().equals(routineWeek.getDayOfWeek()))
                    .findFirst()
                    .orElseThrow(RuntimeException::new).name()
            );
        }

        return weeks;
    }

    @Override
    public void writeRoutine(String token, PostRoutineRequest postRoutineRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(RuntimeException::new);

        Routine routine = routineRepository.save(
                Routine.builder()
                        .title(postRoutineRequest.getTitle())
                        .content(postRoutineRequest.getContent())
                        .email(user.getEmail())
                        .endTime(postRoutineRequest.getEndTime())
                        .priority(postRoutineRequest.getPriority())
                        .startTime(postRoutineRequest.getStartTime())
                        .expType(ExpType.ROUTINE)
                        .isPushed(postRoutineRequest.isPushed())
                        .isSucceed(false)
                        .build()
        );

        for(Weeks weeks : postRoutineRequest.getWeeks()) {
            if(routineWeekRepository.existsByRoutineIdAndDayOfWeek(routine.getRoutineId(), weeks.getDayOfWeek()))
                routineWeekRepository.save(
                        RoutineWeek.builder()
                                .routineId(routine.getRoutineId())
                                .dayOfWeek(weeks.getDayOfWeek())
                                .build()
                );
        }

        achieveUtil.achievePlannerNum(
                routineRepository.countByEmail(user.getEmail()),
                userLevel,
                true,
                true
        );
    }

    @Override
    public List<RoutineResponse> readRoutine(String token, Integer pageNum) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Page<RoutineResponse> routines = routineRepository
                .findAllByEmailAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualOrderByStartTimeAsc(
                        user.getEmail(),
                        LocalTime.now(),
                        LocalTime.now(),
                        PageRequest.of(pageNum, PAGE_NUM)
                );

        return routines.toList();
    }

    @Override
    public List<RoutineResponse> mainRoutine(String token) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Page<RoutineResponse> routines = routineRepository
                .findAllByEmailAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualOrderByStartTimeAsc(
                        user.getEmail(),
                        LocalTime.now(),
                        LocalTime.now(),
                        PageRequest.of(0, MAIN_PAGE_NUM)
                );

        return routines.toList();
    }

    @Override
    @Transactional
    public void updateRoutineWeek(String token, UpdateDayOfWeekRequest updateDayOfWeekRequest, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RuntimeException::new);

        routineWeekRepository.deleteAllByRoutineId(routine.getRoutineId());

        for(Weeks weeks : updateDayOfWeekRequest.getDayOfWeeks()) {
            routineWeekRepository.save(
                    RoutineWeek.builder()
                            .dayOfWeek(weeks.getDayOfWeek())
                            .routineId(routine.getRoutineId())
                            .build()
            );
        }
    }

    @Override
    public void updateRoutineTime(String token, UpdateTimeRequest updateTimeRequest, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RuntimeException::new);

        notNull.setIfNotNull(routine::setStartTime, updateTimeRequest.getStartTime());
        notNull.setIfNotNull(routine::setEndTime, updateTimeRequest.getEndTime());
    }

    @Override
    public void updateTitleAndContent(String token, UpdateTitleAndContentRequest updateTitleAndContentRequest, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RuntimeException::new);

        notNull.setIfNotNull(routine::setTitle, updateTitleAndContentRequest.getTitle());
        notNull.setIfNotNull(routine::setContent, updateTitleAndContentRequest.getContent());
    }

    @Override
    public void updateIsPushed(String token, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(jwtProvider.getEmail(token)))
                .orElseThrow(RuntimeException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RuntimeException::new);

        routineRepository.save(
                routine.updatePushed()
        );
    }

    @Override
    public void updatePriority(String token, UpdateRoutinePriorityRequest updateRoutinePriorityRequest, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RuntimeException::new);

        routineRepository.save(
                routine.updatePriority(updateRoutinePriorityRequest.getPriority())
        );
    }

    @Override
    public void checkRoutine(String token, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RuntimeException::new);


        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(RuntimeException::new);

        userLevelUtil.userLevelManagement(userLevel, routine.getExpType());

        routineRepository.save(
                routine.updateSucceed()
        );

        achieveUtil.achievePlannerNum(
                routineRepository.countByIsSucceedAndEmail(true, user.getEmail()),
                userLevel,
                false,
                true
        );
    }

    @Override
    @Transactional
    public void deleteRoutine(String token, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RuntimeException::new);

        routineRepository.deleteByRoutineId(routine.getRoutineId());
        routineWeekRepository.deleteAllByRoutineId(routine.getRoutineId());
    }
}
