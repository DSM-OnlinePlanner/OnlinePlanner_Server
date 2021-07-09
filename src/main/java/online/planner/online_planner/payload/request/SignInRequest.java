package online.planner.online_planner.payload.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class SignInRequest {
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private String deviceToken;
}
