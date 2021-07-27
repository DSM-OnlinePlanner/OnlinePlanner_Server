package online.planner.online_planner.error.exception;

import lombok.Getter;

@Getter
public class OnlinePlannerException extends RuntimeException {

    private final ErrorCode errorCode;

    public OnlinePlannerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public OnlinePlannerException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
