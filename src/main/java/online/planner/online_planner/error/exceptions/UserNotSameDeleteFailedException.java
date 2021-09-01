package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class UserNotSameDeleteFailedException extends OnlinePlannerException {
    public UserNotSameDeleteFailedException() {
        super(ErrorCode.USER_NOT_SAME_DELETE_FAILED);
    }
}
