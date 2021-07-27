package online.planner.online_planner.error.exceptions;

import online.planner.online_planner.error.exception.ErrorCode;
import online.planner.online_planner.error.exception.OnlinePlannerException;

public class GoalNotFoundException extends OnlinePlannerException {
    public GoalNotFoundException() {
        super(ErrorCode.GOAL_NOT_FOUND);
    }
}
