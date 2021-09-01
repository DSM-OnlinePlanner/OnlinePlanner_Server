package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.request.DeleteAccountRequest;
import online.planner.online_planner.payload.request.PasswordChangeRequest;
import online.planner.online_planner.payload.request.SignUpRequest;
import online.planner.online_planner.payload.response.UserResponse;
import online.planner.online_planner.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
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

    @PostMapping("/date")
    public void setSaveDate(@RequestHeader("Authorization") String token,
                            @RequestParam Integer saveDate) {
        userService.setUserSaveDate(token, saveDate);
    }

    @PutMapping
    public void updateName(@RequestHeader("Authorization") String token,
                           @RequestParam String name) {
        userService.updateName(token, name);
    }

    @PutMapping("/password")
    public void updatePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        userService.changePassword(passwordChangeRequest);
    }

    @DeleteMapping("/logout")
    public void logout(@RequestHeader String deviceToken) {
        userService.logout(deviceToken);
    }

    @DeleteMapping("/account")
    public void deleteAccount(@RequestHeader("Authorization") String token,
                              @RequestBody DeleteAccountRequest deleteAccountRequest) {
        userService.deleteAccount(token, deleteAccountRequest);
    }
}
