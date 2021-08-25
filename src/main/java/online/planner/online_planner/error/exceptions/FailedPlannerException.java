package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class FailedPlannerException extends OnlinePlannerException {
    public FailedPlannerException() {
        super(ErrorCode.FAILED_PLANNER);
    }
}
