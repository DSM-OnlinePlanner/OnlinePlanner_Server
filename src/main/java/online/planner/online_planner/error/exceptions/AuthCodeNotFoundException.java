package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class AuthCodeNotFoundException extends OnlinePlannerException {
    public AuthCodeNotFoundException() {
        super(ErrorCode.AUTH_CODE_NOT_FOUND);
    }
}
