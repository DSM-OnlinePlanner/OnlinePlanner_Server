package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.response.CalenderResponse;
import online.planner.online_planner.service.caleander.CalendarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/calender")
@RequiredArgsConstructor
public class CalenderController {

    private final CalendarService calendarService;

    @GetMapping
    public List<CalenderResponse> getCalender(@RequestHeader("Authorization") String token,
                                              @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return calendarService.getCalenders(token, date);
    }
}
