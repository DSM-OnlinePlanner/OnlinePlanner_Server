package online.planner.online_planner.service.goal;

import online.planner.online_planner.payload.request.PostGoalRequest;
import online.planner.online_planner.payload.request.UpdateGoalRequest;
import online.planner.online_planner.payload.response.GoalResponses;

import java.time.LocalDate;

public interface GoalService {
    void writeGoal(String token, PostGoalRequest postGoalRequest);
    GoalResponses readGoal(String token, LocalDate date);
    void updateGoal(String token, Long goalId, UpdateGoalRequest updateGoalRequest);
    void deleteGoal(String token, Long goalId);
    void achieveGoal(String token, Long goalId);
}
