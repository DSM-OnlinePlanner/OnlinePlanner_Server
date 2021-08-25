package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class SucceedRoutineException extends OnlinePlannerException {
    public SucceedRoutineException() {
        super(ErrorCode.SUCCEED_ROUTINE);
    }
}
