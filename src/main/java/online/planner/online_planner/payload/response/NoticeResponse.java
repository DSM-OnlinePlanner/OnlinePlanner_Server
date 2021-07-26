package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class NoticeResponse {
    private Long noticeId;
    private String title;
    private LocalDate noticeDate;
    private LocalTime noticeAt;
}
