package online.planner.online_planner.payload.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReadGoalRequest {
    private LocalDate date;
}
