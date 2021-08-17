package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class UserEmailNotFoundException extends OnlinePlannerException {
    public UserEmailNotFoundException() {
        super(ErrorCode.NOT_SEND_EMAIL);
    }
}
