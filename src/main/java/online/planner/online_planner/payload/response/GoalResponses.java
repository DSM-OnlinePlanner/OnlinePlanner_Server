package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GoalResponses {
    List<GoalResponse> weekGoals;
    List<GoalResponse> monthGoals;
    List<GoalResponse> yearGoals;
}
