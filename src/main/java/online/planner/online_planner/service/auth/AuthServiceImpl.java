package online.planner.online_planner.service.auth;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.token.Token;
import online.planner.online_planner.entity.token.repository.TokenRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.payload.request.SignInRequest;
import online.planner.online_planner.payload.response.TokenResponse;
import online.planner.online_planner.util.AES256;
import online.planner.online_planner.util.JwtProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final JwtProvider jwtProvider;
    private final AES256 aes256;

    @Override
    public TokenResponse signIn(SignInRequest signInRequest) {
        return userRepository.findByEmail(signInRequest.getEmail())
                .filter(user -> user.getPassword().equals(aes256.AES_Decode(signInRequest.getPassword())))
                .map(User::getEmail)
                .map(email -> {
                    String accessToken = jwtProvider.generateAccessToken(email);
                    String refreshToken = jwtProvider.generateRefreshToken(email);

                    tokenRepository.save(
                            Token.builder()
                                    .email(email)
                                    .refreshToken(refreshToken)
                                    .build()
                    );

                    return TokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                }).orElseThrow(RuntimeException::new);
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        if(!jwtProvider.isRefreshToken(refreshToken)) {
            tokenRepository.deleteByRefreshToken(refreshToken);
            throw new RuntimeException();
        }

        if(jwtProvider.validateToken(refreshToken)) {
            tokenRepository.deleteByRefreshToken(refreshToken);
            throw new RuntimeException();
        }

        return tokenRepository.findByRefreshToken(refreshToken)
                .map(token -> {
                    String newRefreshToken = jwtProvider.generateRefreshToken(token.getEmail());
                    return token.updateRefreshToken(refreshToken);
                })
                .map(tokenRepository::save)
                .map(token -> {
                    String accessToken = jwtProvider.generateAccessToken(token.getEmail());
                    return TokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(token.getRefreshToken())
                            .build();
                }).orElseThrow(RuntimeException::new);
    }
}
