package online.planner.online_planner.service.memo;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.memo.Memo;
import online.planner.online_planner.entity.memo.enums.MemoType;
import online.planner.online_planner.entity.memo.repository.MemoRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.entity.user_level.UserLevel;
import online.planner.online_planner.entity.user_level.repository.UserLevelRepository;
import online.planner.online_planner.payload.request.PostMemoRequest;
import online.planner.online_planner.payload.request.UpdateMemoRequest;
import online.planner.online_planner.payload.response.MemoResponse;
import online.planner.online_planner.payload.response.MemoResponses;
import online.planner.online_planner.util.JwtProvider;
import online.planner.online_planner.util.UserLevelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final UserLevelRepository userLevelRepository;

    private final JwtProvider jwtProvider;
    private final UserLevelUtil userLevelUtil;

    @Override
    public void writeMemo(String token, PostMemoRequest postMemoRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        UserLevel userLevel = userLevelRepository.findByEmail(user.getEmail())
                .orElseThrow(RuntimeException::new);

        if(memoRepository.countByEmail(user.getEmail()) <= 0)
            userLevelUtil.userLevelManagement(userLevel, ExpType.FIRST_MEMO);

        memoRepository.save(
                Memo.builder()
                        .email(user.getEmail())
                        .memoType(postMemoRequest.getMemoType())
                        .memoAt(LocalDate.now())
                        .content(postMemoRequest.getMemo())
                        .build()
        );
    }

    @Override
    public MemoResponses readMemo(String token, LocalDate date) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        LocalDate weekStart = date.with(WeekFields.of(Locale.KOREA).dayOfWeek(), 1);
        LocalDate weekEnd = date.with(WeekFields.of(Locale.KOREA).dayOfWeek(), 7);
        LocalDate monthStart = YearMonth.now().atDay(1);
        LocalDate monthEnd = YearMonth.now().atEndOfMonth();

        List<MemoResponse> todayMemos = memoRepository.findAllByEmailAndMemoTypeAndMemoAtOrderByMemoAtAsc(user.getEmail(), MemoType.TODAY, date);
        List<MemoResponse> weekMemos = memoRepository
                .findAllByEmailAndMemoTypeAndMemoAtLessThanEqualAndMemoAtGreaterThanEqual(
                        user.getEmail(),
                        MemoType.WEEK,
                        weekEnd,
                        weekStart
                );
        List<MemoResponse> monthMemos = memoRepository
                .findAllByEmailAndMemoTypeAndMemoAtLessThanEqualAndMemoAtGreaterThanEqual(
                        user.getEmail(),
                        MemoType.MONTH,
                        monthEnd,
                        monthStart
                );

        return MemoResponses.builder()
                .todayMemo(todayMemos)
                .weekMemo(weekMemos)
                .monthMemo(monthMemos)
                .build();
    }

    @Override
    public void updateMemo(String token, Long memoId, UpdateMemoRequest updateMemoRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Memo memo = memoRepository.findByMemoIdAndEmail(memoId, user.getEmail())
                .orElseThrow(RuntimeException::new);

        memoRepository.save(
                memo.updateMemo(updateMemoRequest.getUpdateMemo())
        );
    }

    @Override
    @Transactional
    public void deleteMemo(String token, Long memoId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        memoRepository.findByMemoIdAndEmail(memoId, user.getEmail())
                .orElseThrow(RuntimeException::new);

        memoRepository.deleteByMemoIdAndEmail(memoId, user.getEmail());
    }
}
