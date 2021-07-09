package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class RoutineResponse {
    private Long routineId;
    private String title;
    private String content;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isSuccess;
    private Boolean isPushed;
    private List<String> dayOfWeeks;
}
