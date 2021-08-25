package online.planner.online_planner.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import online.planner.online_planner.entity.planner.enums.Priority;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class RoutineResponse {
    private Long routineId;
    private String title;
    private String content;
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime endTime;
    private Boolean isSuccess;
    private Boolean isPushed;
    private Boolean isFailed;
    private Priority priority;
    private List<String> dayOfWeeks;
}
