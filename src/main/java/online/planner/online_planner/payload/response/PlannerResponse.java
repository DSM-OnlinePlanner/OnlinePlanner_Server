package online.planner.online_planner.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isSuccess;
    private Boolean isPushed;
    private Boolean isFailed;

    public void setPriorityAndWant(Want want, Priority priority) {
        this.want = want;
        this.priority = priority;
    }
}
