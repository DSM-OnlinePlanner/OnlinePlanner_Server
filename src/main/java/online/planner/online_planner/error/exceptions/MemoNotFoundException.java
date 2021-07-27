package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class MemoNotFoundException extends OnlinePlannerException {
    public MemoNotFoundException() {
        super(ErrorCode.MEMO_NOT_FOUND);
    }
}
