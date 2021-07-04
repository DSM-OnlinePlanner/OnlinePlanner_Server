package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String nickName;
    private Integer userLevel;
    private Integer exp;
    private Integer maxExp;
    private String tier;
}
