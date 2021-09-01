package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.request.ReadAchieveRequest;
import online.planner.online_planner.payload.response.AchievementResponse;
import online.planner.online_planner.service.achievement.AchievementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achieve")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping
    public List<AchievementResponse> getAchieve(@RequestHeader("Authorization") String token,
                                                @RequestParam Boolean isSucceed) {
        return achievementService.getAchievement(
                token,
                ReadAchieveRequest.builder()
                        .isSucceed(isSucceed)
                        .build()
        );
    }
}
