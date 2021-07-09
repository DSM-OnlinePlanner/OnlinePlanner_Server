package online.planner.online_planner.service.routine;

import online.planner.online_planner.payload.request.PostRoutineRequest;
import online.planner.online_planner.payload.request.UpdateDayOfWeekRequest;
import online.planner.online_planner.payload.request.UpdateTimeRequest;
import online.planner.online_planner.payload.request.UpdateTitleAndContentRequest;
import online.planner.online_planner.payload.response.RoutineResponse;

import java.util.List;

public interface RoutineService {
    void writeRoutine(String token, PostRoutineRequest postRoutineRequest);
    List<RoutineResponse> readRoutine(String token, Integer pageNum);
    List<RoutineResponse> mainRoutine(String token);
    void updateRoutineWeek(String token, UpdateDayOfWeekRequest updateDayOfWeekRequest, Long routineId);
    void updateRoutineTime(String token, UpdateTimeRequest updateTimeRequest, Long routineId);
    void updateTitleAndContent(String token, UpdateTitleAndContentRequest updateTitleAndContentRequest, Long routineId);
    void updateIsPushed(String token, Long routineId);
    void checkRoutine(String token, Long routineId);
    void deleteRoutine(String token, Long routineId);
}
