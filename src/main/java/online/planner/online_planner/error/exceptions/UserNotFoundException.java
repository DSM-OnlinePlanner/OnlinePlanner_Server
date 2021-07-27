package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class UserNotFoundException extends OnlinePlannerException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
