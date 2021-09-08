package online.planner.online_planner.service.routine;

import lombok.RequiredArgsConstructor;
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
import online.planner.online_planner.error.exceptions.*;
import online.planner.online_planner.payload.request.*;
import online.planner.online_planner.payload.response.PageResponse;
import online.planner.online_planner.payload.response.RoutineResponse;
import online.planner.online_planner.payload.response.SearchRoutineResponse;
import online.planner.online_planner.util.AchieveUtil;
import online.planner.online_planner.util.JwtProvider;
import online.planner.online_planner.util.NotNull;
import online.planner.online_planner.util.UserLevelUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService{

    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;
    private final RoutineWeekRepository routineWeekRepository;
    private final UserLevelRepository userLevelRepository;

    private final JwtProvider jwtProvider;
    private final UserLevelUtil userLevelUtil;
    private final NotNull notNull;
    private final AchieveUtil achieveUtil;

    private final int PAGE_NUM = 40;
    private final int MAIN_PAGE_NUM = 3;

    private List<String> setRoutineWeeks(Long routineId) {
        List<String> weeks = new ArrayList<>();
        List<RoutineWeek> routineWeeks = routineWeekRepository.findAllByRoutine_RoutineId(routineId);

        for(RoutineWeek routineWeek : routineWeeks) {
            weeks.add(
                    Stream.of(Weeks.values())
                            .filter(weeks1 -> weeks1.getDayOfWeek().equals(routineWeek.getDayOfWeek()))
                            .findFirst()
                            .orElseThrow(RuntimeException::new).name()
            );
        }

        return weeks;
    }

    private List<RoutineResponse> setRoutineResponse(Page<Routine> routines) {
        List<RoutineResponse> responses = new ArrayList<>();

        for(Routine routine : routines) {
            responses.add(
                    RoutineResponse.builder()
                            .routineId(routine.getRoutineId())
                            .title(routine.getTitle())
                            .content(routine.getContent())
                            .isSuccess(routine.getIsSucceed())
                            .isPushed(routine.getIsPushed())
                            .priority(routine.getPriority())
                            .isFailed(routine.getIsFailed())
                            .startTime(routine.getStartTime())
                            .endTime(routine.getEndTime())
                            .dayOfWeeks(setRoutineWeeks(routine.getRoutineId()))
                            .build()
            );

            System.out.println(routine.getTitle() + routine);
        }

        return responses;
    }

    @Override
    public void writeRoutine(String token, PostRoutineRequest postRoutineRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(UserLevelNotFoundException::new);

        System.out.println(postRoutineRequest.getWeeks());

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
                        .writeAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                        .isSucceed(false)
                        .isFailed(false)
                        .build()
        );

        for(Weeks weeks : postRoutineRequest.getWeeks()) {
            if(!routineWeekRepository.existsByRoutine_RoutineIdAndDayOfWeek(routine.getRoutineId(), weeks.getDayOfWeek()))
                routineWeekRepository.save(
                        RoutineWeek.builder()
                                .rouId(routine.getRoutineId())
                                .dayOfWeek(weeks.getDayOfWeek())
                                .routine(routine)
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
                .orElseThrow(UserNotFoundException::new);

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        System.out.println(dayOfWeek);

        Page<Routine> routines = routineRepository.findAllByEmailOrderByWriteAtDesc(
                user.getEmail(),
                PageRequest.of(
                        pageNum,
                        PAGE_NUM
                )
        );

        return setRoutineResponse(routines);
    }

    @Override
    public List<RoutineResponse> mainRoutine(String token) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        System.out.println(LocalTime.now(ZoneId.of("Asia/Seoul")));

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        if(dayOfWeek == 1)
            dayOfWeek = 7;
        else
            dayOfWeek -= 1;

        System.out.println(dayOfWeek);

        Page<RoutineWeek> routines = routineWeekRepository
                .findAllByRoutine_EmailAndDayOfWeek(
                        user.getEmail(),
                        dayOfWeek,
                        PageRequest.of(
                                0,
                                MAIN_PAGE_NUM
                        )
                );

        if(!routines.isEmpty()) {
            System.out.println(routines.toList().get(0).getDayOfWeek());
        }

        System.out.println(routines);

        List<RoutineResponse> responses = new ArrayList<>();

        for(RoutineWeek routineWeek : routines) {
            Routine routine = routineRepository.findByRoutineId(routineWeek.getRouId())
                    .orElseThrow(RoutineNotFounException::new);

            responses.add(
                    RoutineResponse.builder()
                            .routineId(routine.getRoutineId())
                            .title(routine.getTitle())
                            .content(routine.getContent())
                            .isSuccess(routine.getIsSucceed())
                            .isPushed(routine.getIsPushed())
                            .priority(routine.getPriority())
                            .isFailed(routine.getIsFailed())
                            .startTime(routine.getStartTime())
                            .endTime(routine.getEndTime())
                            .dayOfWeeks(setRoutineWeeks(routine.getRoutineId()))
                            .build()
            );

            System.out.println(routine.getTitle() + routine);
        }

        System.out.println(responses);

        return responses;
    }

    @Override
    public SearchRoutineResponse searchRoutine(String token, String title) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Page<Routine> routines = routineRepository
                .findAllByEmailAndTitleContainingOrderByStartTimeAsc(
                        user.getEmail(),
                        title,
                        PageRequest.of(
                                0,
                                MAIN_PAGE_NUM
                        )
                );

        return SearchRoutineResponse.builder()
                .routineResponses(setRoutineResponse(routines))
                .searchNum(routineRepository.countByEmailAndTitleContaining(user.getEmail(), title))
                .build();
    }

    @Override
    @Transactional
    public void updateRoutineWeek(String token, UpdateDayOfWeekRequest updateDayOfWeekRequest, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RoutineNotFounException::new);

        routineWeekRepository.deleteAllByRoutine_RoutineId(routine.getRoutineId());

        System.out.println(updateDayOfWeekRequest.getDayOfWeeks());

        List<RoutineWeek> routineWeeks = new ArrayList<>();

        for(Weeks weeks : updateDayOfWeekRequest.getDayOfWeeks()) {
            System.out.println(weeks.toString() + weeks.getDayOfWeek());

            routineWeeks.add(
                    RoutineWeek.builder()
                            .dayOfWeek(weeks.getDayOfWeek())
                            .rouId(routine.getRoutineId())
                            .routine(routine)
                            .build()
            );
        }

        System.out.println(routineWeeks);

        routineWeekRepository.saveAll(routineWeeks);
    }

    @Override
    public PageResponse getMaxPage(String token) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        return PageResponse.builder()
                .maxPage(routineRepository.countByEmail(user.getEmail()) / PAGE_NUM)
                .build();
    }

    @Override
    public void updateRoutineTime(String token, UpdateTimeRequest updateTimeRequest, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RoutineNotFounException::new);

        System.out.println(updateTimeRequest.getStartTime());
        System.out.println(updateTimeRequest.getEndTime());

        System.out.println(routine.getStartTime());

        notNull.setIfNotNull(routine::setStartTime, updateTimeRequest.getStartTime());
        notNull.setIfNotNull(routine::setEndTime, updateTimeRequest.getEndTime());

        routineRepository.save(routine);
    }

    @Override
    public void updateTitleAndContent(String token, UpdateTitleAndContentRequest updateTitleAndContentRequest, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RoutineNotFounException::new);

        notNull.setIfNotNull(routine::setTitle, updateTitleAndContentRequest.getTitle());
        notNull.setIfNotNull(routine::setContent, updateTitleAndContentRequest.getContent());

        routineRepository.save(routine);
    }

    @Override
    public void updateIsPushed(String token, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(jwtProvider.getEmail(token)))
                .orElseThrow(UserNotFoundException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RoutineNotFounException::new);

        routineRepository.save(
                routine.updatePushed()
        );
    }

    @Override
    public void updatePriority(String token, UpdateRoutinePriorityRequest updateRoutinePriorityRequest, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RoutineNotFounException::new);

        routineRepository.save(
                routine.updatePriority(updateRoutinePriorityRequest.getPriority())
        );
    }

    @Override
    public void checkRoutine(String token, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RoutineNotFounException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(UserLevelNotFoundException::new);

        userLevelUtil.userLevelManagement(userLevel, routine.getExpType());

        if(routine.getIsFailed())
            throw new FailedRoutineException();

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
    public void failedRoutine(String token, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RoutineNotFounException::new);

        if(routine.getIsSucceed())
            throw new SucceedRoutineException();

        routineRepository.save(
                routine.updateFailed()
        );
    }

    @Override
    @Transactional
    public void deleteRoutine(String token, Long routineId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Routine routine = routineRepository.findByRoutineIdAndEmail(routineId, user.getEmail())
                .orElseThrow(RoutineNotFounException::new);

        routineRepository.deleteByRoutineId(routine.getRoutineId());
        routineWeekRepository.deleteAllByRoutine_RoutineId(routine.getRoutineId());
    }
}
