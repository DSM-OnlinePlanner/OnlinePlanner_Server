package online.planner.online_planner.payload.request;

import lombok.Getter;
import online.planner.online_planner.entity.planner.enums.Priority;
import online.planner.online_planner.entity.planner.enums.Want;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class PlannerRequest {
    @NotBlank(message = "title is empty")
    private String title;
    @NotNull
    private String content;
    @NotBlank(message = "priority is empty")
    private Priority priority;
    @NotBlank(message = "want is empty")
    private Want want;
    @NotBlank(message = "startDate is empty")
    private LocalDate startDate;
    @NotBlank(message = "endDate is empty")
    private LocalDate endDate;
    @NotBlank(message = "startTime is empty")
    private LocalTime startTime;
    @NotBlank(message = "endTime is empty")
    private LocalTime endTime;
    @NotBlank(message = "push is empty")
    private Boolean isPushed;
}
