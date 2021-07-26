package online.planner.online_planner.service.achievement;

import online.planner.online_planner.entity.achivement.Achievement;
import online.planner.online_planner.payload.request.ReadAchieveRequest;
import online.planner.online_planner.payload.response.AchievementResponse;

import java.util.List;

public interface AchievementService {
    List<AchievementResponse> getAchievement(String token, ReadAchieveRequest achieveRequest);
}
