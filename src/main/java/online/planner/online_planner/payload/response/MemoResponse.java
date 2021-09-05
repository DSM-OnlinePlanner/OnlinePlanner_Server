package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;
import online.planner.online_planner.entity.memo.enums.MemoType;

import java.time.LocalDate;

@Getter
@Builder
public class MemoResponse {
    private Long memoId;
    private String content;
    private MemoType memoType;
    private LocalDate memoAt;
}
