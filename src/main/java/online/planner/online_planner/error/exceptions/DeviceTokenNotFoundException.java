package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class DeviceTokenNotFoundException extends OnlinePlannerException {
    public DeviceTokenNotFoundException() {
        super(ErrorCode.DEVICE_TOKEN_NOT_FOUND);
    }
}
