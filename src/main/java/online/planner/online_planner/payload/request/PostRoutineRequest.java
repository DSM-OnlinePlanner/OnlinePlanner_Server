package online.planner.online_planner.payload.request;

import lombok.Getter;
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
    @NotBlank(message = "startTime is empty")
    private LocalTime startTime;
    @NotBlank(message = "endTime is empty")
    private LocalTime endTime;
    @NotBlank(message = "push is empty")
    private Boolean isPushed;
}
