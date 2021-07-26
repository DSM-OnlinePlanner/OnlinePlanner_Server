package online.planner.online_planner.service.goal;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.goal.Goal;
import online.planner.online_planner.entity.goal.enums.GoalType;
import online.planner.online_planner.entity.goal.repository.GoalRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.entity.user_level.UserLevel;
import online.planner.online_planner.entity.user_level.repository.UserLevelRepository;
import online.planner.online_planner.payload.request.PostGoalRequest;
import online.planner.online_planner.payload.request.UpdateGoalRequest;
import online.planner.online_planner.payload.response.GoalResponse;
import online.planner.online_planner.payload.response.GoalResponses;
import online.planner.online_planner.util.JwtProvider;
import online.planner.online_planner.util.UserLevelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final UserLevelRepository userLevelRepository;

    private final JwtProvider jwtProvider;
    private final UserLevelUtil userLevelUtil;

    private List<GoalResponse> setGoalList(List<Goal> goals) {
        List<GoalResponse> goalResponses = new ArrayList<>();

        for(Goal goal : goals) {
            goalResponses.add(
                    GoalResponse.builder()
                            .goalDate(goal.getGoalDate())
                            .goalId(goal.getGoalId())
                            .goal(goal.getGoal())
                            .goalType(goal.getGoalType())
                            .isAchieve(goal.isAchieve())
                            .build()
            );
        }

        return goalResponses;
    }

    @Override
    public void writeGoal(String token, PostGoalRequest postGoalRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(RuntimeException::new);

        if(goalRepository.countByEmail(user.getEmail()) <= 0)
            userLevelUtil.userLevelManagement(userLevel, ExpType.FIRST_GOAL);

        goalRepository.save(
                Goal.builder()
                        .email(user.getEmail())
                        .goal(postGoalRequest.getGoal())
                        .goalType(postGoalRequest.getGoalType())
                        .goalDate(LocalDate.now())
                        .isAchieve(false)
                        .expType(ExpType.GOAL)
                        .build()
        );
    }

    @Override
    public GoalResponses readGoal(String token, LocalDate date) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        LocalDate weekStart = date.with(WeekFields.of(Locale.KOREA).dayOfWeek(), 1);
        LocalDate weekEnd = date.with(WeekFields.of(Locale.KOREA).dayOfWeek(), 7);
        LocalDate yearStart = YearMonth.of(date.getYear(), 1).atDay(1);
        LocalDate yearEnd = YearMonth.of(date.getYear(), 12).atEndOfMonth();
        LocalDate monthStart = YearMonth.now().atDay(1);
        LocalDate monthEnd = YearMonth.now().atEndOfMonth();

        List<Goal> weekGoal = goalRepository
                .findAllByEmailAndGoalTypeAndGoalDateGreaterThanEqualAndGoalDateLessThanEqualOrderByGoalDateAsc(
                        user.getEmail(),
                        GoalType.WEEK,
                        weekStart,
                        weekEnd
                );
        List<Goal> monthGoal = goalRepository
                .findAllByEmailAndGoalTypeAndGoalDateGreaterThanEqualAndGoalDateLessThanEqualOrderByGoalDateAsc(
                        user.getEmail(),
                        GoalType.MONTH,
                        monthStart,
                        monthEnd
                );
        List<Goal> yearGoal = goalRepository
                .findAllByEmailAndGoalTypeAndGoalDateGreaterThanEqualAndGoalDateLessThanEqualOrderByGoalDateAsc(
                        user.getEmail(),
                        GoalType.YEAR,
                        yearStart,
                        yearEnd
                );

        List<GoalResponse> weekGoals;
        List<GoalResponse> monthGoals;
        List<GoalResponse> yearGoals;

        weekGoals = setGoalList(weekGoal);
        monthGoals = setGoalList(monthGoal);
        yearGoals = setGoalList(yearGoal);

        return GoalResponses.builder()
                .weekGoals(weekGoals)
                .monthGoals(monthGoals)
                .yearGoals(yearGoals)
                .build();
    }

    @Override
    public void updateGoal(String token, Long goalId, UpdateGoalRequest updateGoalRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Goal goal = goalRepository.findByGoalIdAndEmail(goalId, user.getEmail())
                .orElseThrow(RuntimeException::new);

        goalRepository.save(
                goal.updateGoal(updateGoalRequest.getUpdateGoal())
        );
    }

    @Override
    @Transactional
    public void deleteGoal(String token, Long goalId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        goalRepository.deleteByGoalIdAndEmail(goalId, user.getEmail());
    }

    @Override
    public void achieveGoal(String token, Long goalId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(RuntimeException::new);

        Goal goal = goalRepository.findByGoalIdAndEmail(goalId, user.getEmail())
                .orElseThrow(RuntimeException::new);

        userLevelUtil.userLevelManagement(userLevel, goal.getExpType());

        goalRepository.save(
                goal.updateAchieve()
        );
    }
}
