package online.planner.online_planner.payload.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReadCalenderRequest {
    private LocalDate date;
}
