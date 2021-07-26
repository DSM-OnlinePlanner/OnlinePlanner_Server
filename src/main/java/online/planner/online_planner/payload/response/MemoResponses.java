package online.planner.online_planner.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemoResponses {
    private List<MemoResponse> todayMemo;
    private List<MemoResponse> weekMemo;
    private List<MemoResponse> monthMemo;
}
