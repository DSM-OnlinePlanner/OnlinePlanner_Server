package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class InvalidTokenException extends OnlinePlannerException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
