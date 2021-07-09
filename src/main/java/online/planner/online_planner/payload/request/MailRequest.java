package online.planner.online_planner.payload.request;

import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class MailRequest {
    @Email
    private String email;
}
