package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WebStatisticsResponse {
    private List<PointResponse> sevenDatesPlannerNum;
    private List<PointResponse> fourTeenDatesPlannerNum;
    private List<PointResponse> sevenDatesSuccessNum;
    private List<PointResponse> fourTeenDatesSuccessNum;
}
