package online.planner.online_planner.payload.request;

import lombok.Getter;

@Getter
public class DeleteAccountRequest {
    private String email;
    private String password;
}
