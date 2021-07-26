package online.planner.online_planner.payload.request;

import lombok.Getter;
import online.planner.online_planner.entity.memo.enums.MemoType;

@Getter
public class PostMemoRequest {
    private String memo;
    private MemoType memoType;
}
