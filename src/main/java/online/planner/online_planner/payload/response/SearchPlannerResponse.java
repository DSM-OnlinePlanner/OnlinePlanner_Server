package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchPlannerResponse {
    private int searchNum;
    private List<PlannerResponse> plannerResponses;
}
