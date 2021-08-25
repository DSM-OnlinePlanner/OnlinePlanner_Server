package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class FailedRoutineException extends OnlinePlannerException {
    public FailedRoutineException() {
        super(ErrorCode.FAILED_ROUTINE);
    }
}
