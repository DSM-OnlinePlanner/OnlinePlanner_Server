package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.request.MailAuthRequest;
import online.planner.online_planner.payload.request.MailRequest;
import online.planner.online_planner.service.mail.MailService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping
    public void sendAuthMail(@RequestParam String email,
                             @RequestParam String name) {
        mailService.sendMail(email, name);
    }

    @PostMapping("/password")
    public void sendPasswordChangeMail(@RequestParam String email) {
        mailService.changePasswordMail(email);
    }

    @PostMapping("/auth")
    public void authMail(@Valid @RequestBody MailAuthRequest mailAuthRequest) {
        mailService.authEmail(mailAuthRequest.getCode(), mailAuthRequest.getEmail());
    }

    @PutMapping
    public void resendAuthMail(@RequestParam String email,
                               @RequestParam String name) {
        mailService.resendMail(email, name);
    }

    @PutMapping("/password")
    public void resendPasswordChangeMail(@RequestParam String email) {
        mailService.changePasswordMailResend(email);
    }
}
