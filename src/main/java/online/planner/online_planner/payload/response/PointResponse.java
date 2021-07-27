package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PointResponse {
    private Integer succeedNum;
    private Integer date;
}
