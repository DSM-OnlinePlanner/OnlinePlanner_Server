package online.planner.online_planner.payload.request;

import lombok.Getter;
import online.planner.online_planner.entity.goal.enums.GoalType;

@Getter
public class PostGoalRequest {
    private String goal;
    private GoalType goalType;
}
