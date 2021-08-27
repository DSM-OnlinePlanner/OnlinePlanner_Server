package online.planner.online_planner.service.notice;

import online.planner.online_planner.payload.response.ExistNoticeResponse;
import online.planner.online_planner.payload.response.NoticeResponse;

import java.util.List;

public interface NoticeService {
    List<NoticeResponse> getNotice(String token, Integer pageNum);
    void deleteNotice(String token, Long noticeId);
    ExistNoticeResponse existNotice(String token);
}
