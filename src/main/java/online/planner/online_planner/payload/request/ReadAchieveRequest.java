package online.planner.online_planner.payload.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadAchieveRequest {
    private boolean isSucceed;
}
