package online.planner.online_planner.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import online.planner.online_planner.entity.auth_code.AuthCode;
import online.planner.online_planner.entity.auth_code.repository.AuthCodeRepository;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.error.exceptions.*;
import online.planner.online_planner.util.AES256;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final AuthCodeRepository authCodeRepository;
    private final UserRepository userRepository;

    private final JavaMailSender javaMailSender;

    private final AES256 aes256;

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
            userRepository.findByEmail(email)
                    .orElseThrow(AlreadyUserSignedException::new);

            return true;
        }catch (AlreadyUserSignedException ignored) {
            return false;
        }
    }

    private boolean checkAlreadySendMail(String email) {
        try {
            authCodeRepository.findByEmail(email)
                    .orElseThrow(AlreadyMailSendException::new);

            return true;
        }catch (AlreadyMailSendException e) {
            return false;
        }
    }

    @SneakyThrows
    @Transactional
    public void send(String email, String name) {
        if(email.isEmpty()) {
            throw new EmailBadRequestException();
        }else if(checkSameUser(email)) {
            throw new AlreadyUserSignedException();
        }else {
            String key = getKey();

            authCodeRepository.save(
                    AuthCode.builder()
                            .code(aes256.AES_Encode(key))
                            .email(email)
                            .build()
            );

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("OnlinePlanner를 이용해주셔서 감사합니다.");
            mimeMessageHelper.setText("인증번호 : " + key);

            javaMailSender.send(message);
        }
    }

    @SneakyThrows
    @Transactional
    public void sendPasswordMail(String email) {
        if (checkSameUser(email))
            throw new AlreadyMailSendException();
        else {
            String key = getKey();

            authCodeRepository.save(
                    AuthCode.builder()
                            .email(email)
                            .code(key)
                            .build()
            );

            String title = "OnlinePlanner를 이용해 주셔서 감사합니다.";
            String content = "비밀번호를 바꾸기 위해 다음 인증 코드를 입력해주세요!\n" + key;

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(content);
            mimeMessageHelper.setTo(email);

            javaMailSender.send(message);
        }
    }

    @Override
    public void sendMail(String email, String name) {
        if(checkAlreadySendMail(email)) {
            throw new AlreadyMailSendException();
        }else {
            send(email, name);
        }
    }

    @Override
    public void resendMail(String email, String name) {
        if(checkAlreadySendMail(email))
            send(email, name);
        else
            throw new  UserEmailNotFoundException();
    }

    @Override
    public void changePasswordMail(String email) {
        sendPasswordMail(email);
    }

    @Override
    public void changePasswordMailResend(String email) {
        sendPasswordMail(email);
    }

    @Override
    @Transactional
    public void authEmail(String code, String email) {
        AuthCode authCode = authCodeRepository.findByEmail(email)
                .orElseThrow(AuthCodeNotFoundException::new);

        if(aes256.AES_Decode(authCode.getCode()).equals(code))
            authCodeRepository.deleteByEmail(email);
        else
            throw new AuthCodeAuthFailedException();
    }
}
