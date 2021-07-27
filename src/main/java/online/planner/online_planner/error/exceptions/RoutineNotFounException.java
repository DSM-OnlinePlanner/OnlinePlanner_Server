package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class RoutineNotFounException extends OnlinePlannerException {
    public RoutineNotFounException() {
        super(ErrorCode.ROUTINE_NOT_FOUND);
    }
}
