package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.request.ReadCalenderRequest;
import online.planner.online_planner.payload.response.CalenderResponse;
import online.planner.online_planner.service.caleander.CalendarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calender")
@RequiredArgsConstructor
public class CalenderController {

    private final CalendarService calendarService;

    @GetMapping
    public List<CalenderResponse> getCalender(@RequestHeader("Authorization") String token,
                                              @RequestBody ReadCalenderRequest readCalenderRequest) {
        return calendarService.getCalenders(token, readCalenderRequest.getDate());
    }
}
