package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.request.PostGoalRequest;
import online.planner.online_planner.payload.request.ReadGoalRequest;
import online.planner.online_planner.payload.request.UpdateGoalRequest;
import online.planner.online_planner.payload.response.GoalResponses;
import online.planner.online_planner.service.goal.GoalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public GoalResponses getMyGoals(@RequestHeader("Authorization") String token,
                                    @RequestBody ReadGoalRequest readGoalRequest) {
        return goalService.readGoal(token, readGoalRequest.getDate());
    }

    @PostMapping
    public void writeGoal(@RequestHeader("Authorization") String token,
                          @RequestBody PostGoalRequest postGoalRequest) {
        goalService.writeGoal(token, postGoalRequest);
    }

    @PutMapping("/{goalId}")
    public void updateGoal(@RequestHeader("Authorization") String token,
                           @RequestBody UpdateGoalRequest updateGoalRequest,
                           @PathVariable Long goalId) {
        goalService.updateGoal(token, goalId, updateGoalRequest);
    }

    @PutMapping("/achieve/{goalId}")
    public void achieveGoal(@RequestHeader("Authorization") String token,
                            @PathVariable Long goalId) {
        goalService.achieveGoal(token, goalId);
    }

    @DeleteMapping("/{goalId}")
    public void deleteGoal(@RequestHeader("Authorization") String token,
                           @PathVariable Long goalId) {
        goalService.deleteGoal(token, goalId);
    }
}
