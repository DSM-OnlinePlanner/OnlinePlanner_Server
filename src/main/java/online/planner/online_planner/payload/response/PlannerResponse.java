package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;
import online.planner.online_planner.entity.planner.enums.Priority;
import online.planner.online_planner.entity.planner.enums.Want;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class PlannerResponse {
    private long plannerId;
    private String title;
    private String content;
    private Priority priority;
    private Want want;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isSuccess;
}
