package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.request.SignUpRequest;
import online.planner.online_planner.payload.response.UserResponse;
import online.planner.online_planner.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserResponse getMyInfo(@RequestHeader("Authorization") String token) {
        return userService.getUserInfo(token);
    }

    @PostMapping
    public void signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
    }

    @PutMapping
    public void updateName(@RequestHeader("Authorization") String token,
                           @RequestParam String name) {
        userService.updateName(token, name);
    }
}
