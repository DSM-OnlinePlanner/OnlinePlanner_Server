package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class UserLevelNotFoundException extends OnlinePlannerException {
    public UserLevelNotFoundException() {
        super(ErrorCode.USER_LEVEL_NOT_FOUND);
    }
}
