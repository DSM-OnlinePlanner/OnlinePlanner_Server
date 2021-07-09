package online.planner.online_planner.service.planner;

import online.planner.online_planner.payload.request.PlannerRequest;
import online.planner.online_planner.payload.request.UpdateDateRequest;
import online.planner.online_planner.payload.request.UpdateTimeRequest;
import online.planner.online_planner.payload.request.UpdateTitleAndContentRequest;
import online.planner.online_planner.payload.response.PlannerResponse;

import java.util.List;

public interface PlannerService {
    void postPlanner(String token, PlannerRequest plannerRequest);
    List<PlannerResponse> readPlanner(String token, Integer pageNum);
    List<PlannerResponse> mainPlanner(String token);
    void checkSuccess(String token, Long plannerId);
    void updatePlannerTitleAndContent(String token, UpdateTitleAndContentRequest updateTitleAndContentRequest, Long plannerId);
    void updatePlannerDate(String token, UpdateDateRequest updateDateRequest, Long plannerId);
    void updatePlannerTime(String token, UpdateTimeRequest updateTimeRequest, Long plannerId);
    void updatePlannerPushed(String token, Long routineId);
    void deletePlanner(String token, Long plannerId);
}
