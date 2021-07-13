package online.planner.online_planner.payload.request;

import lombok.Getter;

@Getter
public class PasswordChangeRequest {
    private String email;
    private String password;
}
