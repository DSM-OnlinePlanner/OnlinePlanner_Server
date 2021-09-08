package online.planner.online_planner.service.planner;

import online.planner.online_planner.payload.request.*;
import online.planner.online_planner.payload.response.PageResponse;
import online.planner.online_planner.payload.response.PlannerResponse;
import online.planner.online_planner.payload.response.SearchPlannerResponse;

import java.time.LocalDate;
import java.util.List;

public interface PlannerService {
    void postPlanner(String token, PlannerRequest plannerRequest);
    List<PlannerResponse> readPlanner(String token, LocalDate date, Integer pageNum);
    List<PlannerResponse> mainPlanner(String token, LocalDate date);
    SearchPlannerResponse searchPlanner(String token, String title);
    PageResponse getMaxPage(String token);
    void checkSuccess(String token, Long plannerId);
    void updatePlannerTitleAndContent(String token, UpdateTitleAndContentRequest updateTitleAndContentRequest, Long plannerId);
    void updatePlannerDate(String token, UpdateDateRequest updateDateRequest, Long plannerId);
    void updatePlannerTime(String token, UpdateTimeRequest updateTimeRequest, Long plannerId);
    void updatePlannerPushed(String token, Long plannerId);
    void updatePriority(String token, UpdatePlannerPriorityRequest updatePlannerPriorityRequest, Long plannerId);
    void latePlanner(String token, LatePlannerRequest latePlannerRequest, Long plannerId);
    void failedPlanner(String token, Long plannerId);
    void deletePlanner(String token, Long plannerId);
}
