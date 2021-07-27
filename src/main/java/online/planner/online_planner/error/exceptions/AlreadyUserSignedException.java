package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class AlreadyUserSignedException extends OnlinePlannerException {
    public AlreadyUserSignedException() {
        super(ErrorCode.ALREADY_USER_SIGNED);
    }
}
