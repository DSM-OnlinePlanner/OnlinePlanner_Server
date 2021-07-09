package online.planner.online_planner.service.auth;

import online.planner.online_planner.payload.request.NDSignInRequest;
import online.planner.online_planner.payload.request.SignInRequest;
import online.planner.online_planner.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse signIn(SignInRequest signInRequest);
    TokenResponse signInND(NDSignInRequest ndSignInRequest);
    TokenResponse refreshToken(String refreshToken);
}
