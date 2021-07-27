package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class RefreshTokenNotFoundException extends OnlinePlannerException {
    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
