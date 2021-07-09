package online.planner.online_planner.payload.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class SignUpRequest {
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String nickName;
}
