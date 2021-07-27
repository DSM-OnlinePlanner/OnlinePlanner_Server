package online.planner.online_planner.service.notice;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.notice.Notice;
import online.planner.online_planner.entity.notice.repository.NoticeRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.error.exceptions.NoticeNotFoundException;
import online.planner.online_planner.error.exceptions.UserNotFoundException;
import online.planner.online_planner.payload.response.NoticeResponse;
import online.planner.online_planner.util.JwtProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final int PAGE_NUM = 10;

    @Override
    public List<NoticeResponse> getNotice(String token, Integer pageNum) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        Page<NoticeResponse> notices = noticeRepository.findAllByEmail(user.getEmail(), PageRequest.of(pageNum, PAGE_NUM));

        return notices.toList();
    }

    @Override
    @Transactional
    public void deleteNotice(String token, Long noticeId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        noticeRepository.findByNoticeIdAndEmail(noticeId, user.getEmail())
                .orElseThrow(NoticeNotFoundException::new);

        noticeRepository.deleteByEmailAndNoticeId(user.getEmail(), noticeId);
    }
}
