package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class AlreadyMailSendException extends OnlinePlannerException {
    public AlreadyMailSendException() {
        super(ErrorCode.ALREADY_USER_MAIL_SEND);
    }
}
