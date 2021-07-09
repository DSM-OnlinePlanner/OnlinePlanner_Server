package online.planner.online_planner.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
public class UpdateTimeRequest {
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
}
