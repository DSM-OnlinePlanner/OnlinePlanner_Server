package online.planner.online_planner.service.mail;

public interface MailService {
    void sendMail(String email, String name);
    void resendMail(String email, String name);
    void changePasswordMail(String email);
    void changePasswordMailResend(String email);
    void authEmail(String code, String email);
}
