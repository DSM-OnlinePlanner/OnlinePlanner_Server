package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class IsNotRefreshTokenException extends OnlinePlannerException {
    public IsNotRefreshTokenException() {
        super(ErrorCode.IS_NOT_REFRESH_TOKEN);
    }
}
