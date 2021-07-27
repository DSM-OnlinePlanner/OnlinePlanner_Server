package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class EmailBadRequestException extends OnlinePlannerException {
    public EmailBadRequestException() {
        super(ErrorCode.EMAIL_BAD_REQUEST);
    }
}
