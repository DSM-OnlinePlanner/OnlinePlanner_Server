package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;
import online.planner.online_planner.entity.goal.enums.GoalType;

import java.time.LocalDate;

@Getter
@Builder
public class GoalResponse {
    private Long goalId;
    private String goal;
    private GoalType goalType;
    private LocalDate goalDate;
    private boolean isAchieve;
}
