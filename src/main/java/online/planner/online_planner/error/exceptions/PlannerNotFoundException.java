package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class PlannerNotFoundException extends OnlinePlannerException {
    public PlannerNotFoundException() {
        super(ErrorCode.PLANNER_NOT_FOUND);
    }
}
