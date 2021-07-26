package online.planner.online_planner.service.memo;

import online.planner.online_planner.payload.request.PostMemoRequest;
import online.planner.online_planner.payload.request.UpdateMemoRequest;
import online.planner.online_planner.payload.response.MemoResponses;

import java.time.LocalDate;

public interface MemoService {
    void writeMemo(String token, PostMemoRequest postMemoRequest);
    MemoResponses readMemo(String token, LocalDate date);
    void updateMemo(String token, Long memoId, UpdateMemoRequest updateMemoRequest);
    void deleteMemo(String token, Long memoId);
}
