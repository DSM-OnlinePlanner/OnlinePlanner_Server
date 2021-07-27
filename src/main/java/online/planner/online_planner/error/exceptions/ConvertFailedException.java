package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class ConvertFailedException extends OnlinePlannerException {
    public ConvertFailedException() {
        super(ErrorCode.CONVERT_FAILED);
    }
}
