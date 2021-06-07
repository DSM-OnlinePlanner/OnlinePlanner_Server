package online.planner.online_planner.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import online.planner.online_planner.entity.auth_code.AuthCode;
import online.planner.online_planner.entity.auth_code.repository.AuthCodeRepository;
import online.planner.online_planner.entity.user.repository.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final AuthCodeRepository authCodeRepository;
    private final UserRepository userRepository;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    private String getKey() {
        StringBuilder key = new StringBuilder();

        for(int i = 0; i < 6; i++) {
            int div = (int)Math.floor(Math.random() * 2);
            if(div == 0) {
                key.append((char) (Math.random() * 10 + '0'));
            }else {
                key.append((char)(Math.random()*26 + 'A'));
            }
        }

        return key.toString();
    }

    private boolean checkSameUser(String email) {
        try {
            userRepository.findByEmail(email);

            return false;
        }catch (Exception e) {
            return true;
        }
    }

    @SneakyThrows
    @Transactional
    public void send(String email, String name) {
        if(email.isEmpty()) {
            throw new RuntimeException();
        }else if(checkSameUser(email)) {
            throw new RuntimeException();
        }else {
            String key = getKey();

            authCodeRepository.save(
                    AuthCode.builder()
                            .code(key)
                            .email(email)
                            .build()
            );

            Context context = new Context();
            context.setVariable("code", key);
            context.setVariable("email", email);
            context.setVariable("name", name);

            String mail = templateEngine.process("mail-template.html", context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("OnlinePlanner를 이용해주셔서 감사합니다.");
            mimeMessageHelper.setText(mail, true);

            javaMailSender.send(message);
        }
    }

    @Override
    @Async
    public void sendMail(String email, String name) {
        send(email, name);
    }

    @Override
    @Async
    public void resendMail(String email, String name) {
        send(email, name);
    }

    @Transactional
    @Override
    public void authEmail(String code, String email) {
        AuthCode authCode = authCodeRepository.findByCodeAndEmail(code, email)
                .orElseThrow(RuntimeException::new);

        if (!authCode.getCode().equals(code)) {
            throw new RuntimeException();
        } else {
            authCodeRepository.deleteByCodeAndEmail(code, email);
        }
    }
}
