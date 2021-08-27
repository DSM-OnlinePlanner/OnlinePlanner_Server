package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchRoutineResponse {
    private int searchNum;
    private List<RoutineResponse> routineResponses;
}
