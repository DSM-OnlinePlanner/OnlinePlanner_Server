package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class SucceedPlannerException extends OnlinePlannerException {
    public SucceedPlannerException() {
        super(ErrorCode.SUCCEED_PLANNER);
    }
}
