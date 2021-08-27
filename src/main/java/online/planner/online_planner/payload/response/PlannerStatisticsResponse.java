package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlannerStatisticsResponse {
    private Double statistics;
}
