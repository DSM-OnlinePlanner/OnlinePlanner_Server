package online.planner.online_planner.payload.request;

import lombok.Getter;

@Getter
public class SignInRequest {
    private String email;
    private String password;
    private String deviceToken;
}
