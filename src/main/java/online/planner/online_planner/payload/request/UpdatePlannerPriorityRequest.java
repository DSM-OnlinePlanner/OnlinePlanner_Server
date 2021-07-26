package online.planner.online_planner.payload.request;

import lombok.Getter;
import online.planner.online_planner.entity.planner.enums.Priority;
import online.planner.online_planner.entity.planner.enums.Want;

@Getter
public class UpdatePlannerPriorityRequest {
    private Priority priority;
    private Want want;
}
