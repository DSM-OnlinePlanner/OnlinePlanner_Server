package online.planner.online_planner.error;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OnlinePlannerExceptionHandler {

    @ExceptionHandler(OnlinePlannerException.class)
    protected ResponseEntity<ErrorResponse> handlerException(final OnlinePlannerException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(errorCode.getStatus(), errorCode.getMessage()),
                HttpStatus.valueOf(errorCode.getStatus()));
    }
}
