package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    String accessToken;
    String refreshToken;
}
