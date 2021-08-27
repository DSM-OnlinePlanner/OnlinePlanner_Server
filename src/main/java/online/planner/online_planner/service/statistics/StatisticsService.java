package online.planner.online_planner.service.statistics;

import online.planner.online_planner.payload.response.PlannerStatisticsResponse;
import online.planner.online_planner.payload.response.StatisticsResponse;

public interface StatisticsService {
    StatisticsResponse getMyStatistics(String token);
    PlannerStatisticsResponse getStatistics(String token);
}
