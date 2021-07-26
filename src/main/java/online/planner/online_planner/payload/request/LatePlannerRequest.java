package online.planner.online_planner.payload.request;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class LatePlannerRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
