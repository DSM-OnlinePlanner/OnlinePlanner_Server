package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class AchievementNotFoundException extends OnlinePlannerException {
    public AchievementNotFoundException() {
        super(ErrorCode.ACHIEVEMENT_NOT_FOUND);
    }
}
