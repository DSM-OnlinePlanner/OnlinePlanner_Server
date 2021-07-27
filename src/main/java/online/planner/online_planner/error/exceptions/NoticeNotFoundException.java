package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class NoticeNotFoundException extends OnlinePlannerException {
    public NoticeNotFoundException() {
        super(ErrorCode.NOTICE_NOT_FOUND);
    }
}
