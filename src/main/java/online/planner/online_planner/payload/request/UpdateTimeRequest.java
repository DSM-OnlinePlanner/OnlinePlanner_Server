package online.planner.online_planner.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
public class UpdateTimeRequest {
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime endTime;
}
