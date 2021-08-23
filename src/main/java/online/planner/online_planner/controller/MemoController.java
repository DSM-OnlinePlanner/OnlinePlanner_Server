package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.request.PostMemoRequest;
import online.planner.online_planner.payload.request.UpdateMemoRequest;
import online.planner.online_planner.payload.response.MemoResponses;
import online.planner.online_planner.service.memo.MemoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/memo")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping
    public MemoResponses getMyMemos(@RequestHeader("Authorization") String token,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return memoService.readMemo(token, date);
    }

    @PostMapping
    public void writeMemo(@RequestHeader("Authorization") String token,
                          @RequestBody PostMemoRequest postMemoRequest) {
        memoService.writeMemo(token, postMemoRequest);
    }

    @PutMapping("/{memoId}")
    public void updateMemo(@RequestHeader("Authorization") String token,
                           @RequestBody UpdateMemoRequest  updateMemoRequest,
                           @PathVariable Long memoId) {
        memoService.updateMemo(token, memoId, updateMemoRequest);
    }

    @DeleteMapping("/{memoId}")
    public void deleteMemo(@RequestHeader("Authorization") String token,
                           @PathVariable Long memoId) {
        memoService.deleteMemo(token, memoId);
    }
}
