package online.planner.online_planner.service.user;

import online.planner.online_planner.payload.request.SignUpRequest;
import online.planner.online_planner.payload.response.UserResponse;

public interface UserService {
    void signUp(SignUpRequest signUpRequest);
    void updateName(String token, String nickName);
    UserResponse getUserInfo(String token);
}
