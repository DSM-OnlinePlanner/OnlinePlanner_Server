package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.request.*;
import online.planner.online_planner.payload.response.PlannerResponse;
import online.planner.online_planner.service.planner.PlannerService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/planner")
@RequiredArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;

    @GetMapping("/{pageNum}")
    public List<PlannerResponse> readPlanner(@RequestHeader("Authorization") String token,
                                             @Valid @RequestBody PlannerReadRequest plannerReadRequest,
                                             @PathVariable Integer pageNum) {
        return plannerService.readPlanner(token, plannerReadRequest, pageNum);
    }

    @GetMapping("/main")
    public List<PlannerResponse> getMainPlannerList(@RequestHeader("Authorization") String token,
                                                    @RequestBody PlannerReadRequest plannerReadRequest) {
        return plannerService.mainPlanner(token, plannerReadRequest);
    }

    @PostMapping
    public void postPlanner(@RequestHeader("Authorization") String token,
                            @Valid @RequestBody PlannerRequest plannerRequest) {
        System.out.println(plannerRequest.getEndDate());
        System.out.println(plannerRequest.getEndTime());
        plannerService.postPlanner(token, plannerRequest);
    }

    @PutMapping("/check/{plannerId}")
    public void checkSuccess(@PathVariable Long plannerId,
                             @RequestHeader("Authorization") String token) {
        plannerService.checkSuccess(token, plannerId);
    }

    @PutMapping("/{plannerId}")
    public void updateTitleAndContent(@RequestHeader("Authorization") String token,
                                      @Valid @RequestBody UpdateTitleAndContentRequest updateTitleAndContentRequest,
                                      @PathVariable Long plannerId) {
        plannerService.updatePlannerTitleAndContent(token, updateTitleAndContentRequest, plannerId);
    }

    @PutMapping("/date/{plannerId}")
    public void updatePlannerDare(@RequestHeader("Authorization") String token,
                                  @Valid @RequestBody UpdateDateRequest updateDateRequest,
                                  @PathVariable Long plannerId) {
        plannerService.updatePlannerDate(token, updateDateRequest, plannerId);
    }

    @PutMapping("/time/{plannerId}")
    public void updatePlannerTime(@RequestHeader("Authorization") String token,
                                  @Valid @RequestBody UpdateTimeRequest updateTimeRequest,
                                  @PathVariable Long plannerId) {
        plannerService.updatePlannerTime(token, updateTimeRequest, plannerId);
    }

    @PutMapping("/push/{plannerId}")
    public void updatePlannerPushed(@RequestHeader("Authorization") String token,
                                    @PathVariable Long plannerId) {
        plannerService.updatePlannerPushed(token, plannerId);
    }

    @DeleteMapping("/{plannerId}")
    public void deletePlanner(@RequestHeader("Authorization") String token,
                              @PathVariable Long plannerId) {
        plannerService.deletePlanner(token, plannerId);
    }
}
