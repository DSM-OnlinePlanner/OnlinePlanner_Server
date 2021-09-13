package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.response.PlannerStatisticsResponse;
import online.planner.online_planner.payload.response.StatisticsResponse;
import online.planner.online_planner.payload.response.WebStatisticsResponse;
import online.planner.online_planner.service.statistics.StatisticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public StatisticsResponse getStatistics(@RequestHeader("Authorization") String token) {
        return statisticsService.getMyStatistics(token);
    }

    @GetMapping("/planner")
    public PlannerStatisticsResponse getPlannerStatistics(@RequestHeader("Authorization") String token) {
        return statisticsService.getStatistics(token);
    }

    @GetMapping("/web")
    public WebStatisticsResponse getWebStatistics(@RequestHeader("Authorization") String token) {
        return statisticsService.getWebStatics(token);
    }
}
