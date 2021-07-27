package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StatisticsResponse {
    private int maxWeek;
    private int weekSucceed;
    private int maxMonth;
    private int monthSucceed;
    private List<PointResponse> pointResponses;
}
