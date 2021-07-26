package online.planner.online_planner.service.caleander;

import online.planner.online_planner.payload.response.CalenderResponse;

import java.time.LocalDate;
import java.util.List;

public interface CalendarService {
    List<CalenderResponse> getCalenders(String token, LocalDate date);
}
