package online.planner.online_planner.payload.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class MailAuthRequest {
    @Email
    private String email;
    @NotBlank
    private String code;
}
