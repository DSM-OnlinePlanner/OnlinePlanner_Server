package online.planner.online_planner.payload.request;

import lombok.Getter;
import online.planner.online_planner.entity.routine_date.enums.Weeks;

import java.util.List;

@Getter
public class UpdateDayOfWeekRequest {
    List<Weeks> dayOfWeeks;
}
