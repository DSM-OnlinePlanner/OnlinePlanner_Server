package online.planner.online_planner.payload.request;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class UpdateTimeRequest {
    private LocalTime startTime;
    private LocalTime endTime;
}
