package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class DecodeFailedException extends OnlinePlannerException {
    public DecodeFailedException() {
        super(ErrorCode.DECODING_FAILED);
    }
}
