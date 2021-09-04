package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;
import online.planner.online_planner.entity.planner.enums.Priority;
import online.planner.online_planner.entity.planner.enums.Want;

import java.time.LocalDate;

@Getter
@Builder
public class CalenderResponse {
    private Want want;
    private boolean isSucceed;
    private LocalDate startDate;
    private LocalDate endDate;
}
