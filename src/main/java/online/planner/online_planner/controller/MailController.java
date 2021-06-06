package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.request.MailAuthRequest;
import online.planner.online_planner.payload.request.MailRequest;
import online.planner.online_planner.service.mail.MailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping
    public void sendAuthMail(@RequestParam String email) {
        mailService.sendMail(email);
    }

    @PutMapping
    public void resendAuthMail(@RequestParam String email) {
        mailService.resendMail(email);
    }

    @PostMapping("/auth")
    public void authMail(@RequestBody MailAuthRequest mailAuthRequest) {
        mailService.authEmail(mailAuthRequest.getCode(), mailAuthRequest.getEmail());
    }
}
