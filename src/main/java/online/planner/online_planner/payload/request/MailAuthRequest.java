package online.planner.online_planner.payload.request;

import lombok.Getter;

@Getter
public class MailAuthRequest {
    private String email;
    private String code;
}
