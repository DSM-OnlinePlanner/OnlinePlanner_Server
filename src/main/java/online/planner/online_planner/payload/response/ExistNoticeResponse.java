package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExistNoticeResponse {
    private Boolean isNoticed;
}
