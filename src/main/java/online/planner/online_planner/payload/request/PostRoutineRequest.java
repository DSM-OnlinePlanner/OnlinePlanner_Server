package online.planner.online_planner.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import online.planner.online_planner.entity.planner.enums.Priority;
import online.planner.online_planner.entity.routine_date.enums.Weeks;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Getter
public class PostRoutineRequest {
    @NotBlank(message = "title is empty")
    private String title;
    @NotNull
    private String content;
    @NotBlank
    private List<Weeks> weeks;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;
    private boolean isPushed;
    private Priority priority;
}
