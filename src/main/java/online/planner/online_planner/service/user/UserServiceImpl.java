package online.planner.online_planner.service.user;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.entity.user_level.UserLevel;
import online.planner.online_planner.entity.user_level.enums.TierLevel;
import online.planner.online_planner.entity.user_level.repository.UserLevelRepository;
import online.planner.online_planner.payload.request.PasswordChangeRequest;
import online.planner.online_planner.payload.request.SignUpRequest;
import online.planner.online_planner.payload.response.UserResponse;
import online.planner.online_planner.util.AES256;
import online.planner.online_planner.util.JwtProvider;
import online.planner.online_planner.util.NotNull;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserLevelRepository userLevelRepository;

    private final JwtProvider jwtProvider;
    private final AES256 aes256;
    private final NotNull notNull;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        User user = userRepository.findByEmail(signUpRequest.getEmail())
                .orElse(null);

        if(user != null)
            throw new RuntimeException();

        User mine = userRepository.save(
                User.builder()
                        .email(signUpRequest.getEmail())
                        .nickName(signUpRequest.getNickName())
                        .password(aes256.AES_Encode(signUpRequest.getPassword()))
                        .saveDate(365)
                        .build()
        );

        userLevelRepository.save(
                UserLevel.builder()
                        .email(mine.getEmail())
                        .tierLevel(TierLevel.ZERO_LV)
                        .userExp(0)
                        .userLv(1)
                        .build()
        );
    }

    @Override
    public void updateName(String token, String nickName) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        userRepository.save(
                user.updateNickName(nickName)
        );
    }

    @Override
    public void setUserSaveDate(String token, Integer saveDate) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        notNull.setIfNotNull(user::setSaveDate, saveDate);
    }

    @Override
    public UserResponse getUserInfo(String token) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(RuntimeException::new);

        return UserResponse.builder()
                .exp(userLevel.getUserExp())
                .nickName(user.getNickName())
                .userLevel(userLevel.getUserLv())
                .maxExp(userLevel.getTierLevel().getMax_exp())
                .tier(userLevel.getTierLevel().getTier())
                .build();
    }

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        userRepository.findByEmail(passwordChangeRequest.getEmail())
                .map(user -> user.updatePassword(aes256.AES_Encode(passwordChangeRequest.getPassword())))
                .map(userRepository::save)
                .orElseThrow(RuntimeException::new);
    }
}
