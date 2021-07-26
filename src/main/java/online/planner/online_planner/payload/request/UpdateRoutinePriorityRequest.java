package online.planner.online_planner.payload.request;

import lombok.Getter;
import online.planner.online_planner.entity.planner.enums.Priority;

@Getter
public class UpdateRoutinePriorityRequest {
    private Priority priority;
}
