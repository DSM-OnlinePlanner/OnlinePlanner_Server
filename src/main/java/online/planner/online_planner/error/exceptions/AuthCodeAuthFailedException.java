package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class AuthCodeAuthFailedException extends OnlinePlannerException {
    public AuthCodeAuthFailedException() {
        super(ErrorCode.AUTH_CODE_FAILED);
    }
}
