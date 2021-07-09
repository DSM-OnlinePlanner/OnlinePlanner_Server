package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.request.PostRoutineRequest;
import online.planner.online_planner.payload.request.UpdateDayOfWeekRequest;
import online.planner.online_planner.payload.request.UpdateTimeRequest;
import online.planner.online_planner.payload.request.UpdateTitleAndContentRequest;
import online.planner.online_planner.payload.response.RoutineResponse;
import online.planner.online_planner.service.routine.RoutineService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/routine")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    @GetMapping("/main")
    public List<RoutineResponse> readRoutineMain(@RequestHeader("Authorization") String token) {
        return routineService.mainRoutine(token);
    }

    @GetMapping("/{pageNum}")
    public List<RoutineResponse> readRoutine(@RequestHeader("Authorization") String token,
                                             @PathVariable Integer pageNum) {
        return routineService.readRoutine(token, pageNum);
    }

    @PostMapping
    public void postRoutine(@RequestHeader("Authorization") String token,
                            @Valid @RequestBody PostRoutineRequest postRoutineRequest) {
        routineService.writeRoutine(token, postRoutineRequest);
    }

    @PutMapping("/{routineId}")
    public void updateTitleAndContent(@RequestHeader("Authorization") String token,
                                      @Valid @RequestBody UpdateTitleAndContentRequest updateTitleAndContentRequest,
                                      @PathVariable Long routineId) {
        routineService.updateTitleAndContent(token, updateTitleAndContentRequest, routineId);
    }

    @PutMapping("/time/{routineId}")
    public void updateRoutineTime(@RequestHeader("Authorization") String token,
                                  @Valid @RequestBody UpdateTimeRequest updateTimeRequest,
                                  @PathVariable Long routineId) {
        routineService.updateRoutineTime(token, updateTimeRequest, routineId);
    }

    @PutMapping("/week/{routineId}")
    public void updateRoutineWeek(@RequestHeader("Authorization") String token,
                                  @Valid @RequestBody UpdateDayOfWeekRequest updateDayOfWeekRequest,
                                  @PathVariable Long routineId) {
        routineService.updateRoutineWeek(token, updateDayOfWeekRequest, routineId);
    }

    @PutMapping("/check/{routineId}")
    public void checkRoutineSuccessed(@RequestHeader("Authorization") String token,
                                      @PathVariable Long routineId) {
        routineService.checkRoutine(token, routineId);
    }

    @PutMapping("/pushed/{routineId}")
    public void updateRoutineIsPushed(@RequestHeader("Authorization") String token,
                                      @PathVariable Long routineId) {
        routineService.updateIsPushed(token, routineId);
    }

    @DeleteMapping("/{routineId}")
    public void deleteRoutine(@RequestHeader("Authorization") String token,
                              @PathVariable Long routineId) {
        routineService.deleteRoutine(token, routineId);
    }
}
