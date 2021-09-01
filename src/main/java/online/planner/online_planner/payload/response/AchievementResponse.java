package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;
import online.planner.online_planner.entity.achivement.enums.Achieve;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class AchievementResponse {
    private Long achieveId;
    private String achieve;
    private boolean isSucceed;
    private LocalDate achieveDate;
    private LocalTime achieveAt;
}
