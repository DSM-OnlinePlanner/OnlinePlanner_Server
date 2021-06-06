package online.planner.online_planner.service.mail;

public interface MailService {
    void sendMail(String email);
    void resendMail(String email);
    void authEmail(String code, String email);
}
