package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class LoginFailedException extends OnlinePlannerException {
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED);
    }
}
