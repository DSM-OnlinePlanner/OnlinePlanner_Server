package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.payload.response.ExistNoticeResponse;
import online.planner.online_planner.payload.response.NoticeResponse;
import online.planner.online_planner.service.notice.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/{pageNum}")
    public List<NoticeResponse> getMyNotice(@RequestHeader("Authorization") String token,
                                            @PathVariable Integer pageNum) {
        return noticeService.getNotice(token, pageNum);
    }

    @GetMapping("/exist")
    public ExistNoticeResponse existNotice(@RequestHeader("Authorization") String token) {
        return noticeService.existNotice(token);
    }

    @DeleteMapping("/{noticeId}")
    public void deleteNotice(@RequestHeader("Authorization") String token,
                             @PathVariable Long noticeId) {
        noticeService.deleteNotice(token, noticeId);
    }
}
