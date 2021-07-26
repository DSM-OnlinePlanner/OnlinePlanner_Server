package online.planner.online_planner.service.memo;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.memo.Memo;
import online.planner.online_planner.entity.memo.enums.MemoType;
import online.planner.online_planner.entity.memo.repository.MemoRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.payload.request.PostMemoRequest;
import online.planner.online_planner.payload.request.UpdateMemoRequest;
import online.planner.online_planner.payload.response.MemoResponse;
import online.planner.online_planner.payload.response.MemoResponses;
import online.planner.online_planner.util.JwtProvider;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Override
    public void writeMemo(String token, PostMemoRequest postMemoRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

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

        List<Memo> todayMemos = memoRepository.findAllByEmailAndMemoTypeAndMemoAtOrderByMemoAtAsc(user.getEmail(), MemoType.TODAY, date);
        List<Memo> weekMemos = memoRepository
                .findAllByEmailAndMemoTypeAndMemoAtLessThanEqualAndMemoAtGreaterThanEqual(
                        user.getEmail(),
                        MemoType.WEEK,
                        weekEnd,
                        weekStart
                );
        List<Memo> monthMemos = memoRepository
                .findAllByEmailAndMemoTypeAndMemoAtLessThanEqualAndMemoAtGreaterThanEqual(
                        user.getEmail(),
                        MemoType.MONTH,
                        monthEnd,
                        monthStart
                );

        List<MemoResponse> todayMemoResponse = new ArrayList<>();
        List<MemoResponse> weekMemoResponse = new ArrayList<>();
        List<MemoResponse> monthMemoResponse = new ArrayList<>();

        for(Memo memo : todayMemos) {
            todayMemoResponse.add(
                    MemoResponse.builder()
                            .memoId(memo.getMemoId())
                            .memoType(memo.getMemoType())
                            .memo(memo.getContent())
                            .memoAt(memo.getMemoAt())
                            .build()
            );
        }
        for(Memo memo : weekMemos) {
            weekMemoResponse.add(
                    MemoResponse.builder()
                            .memoId(memo.getMemoId())
                            .memo(memo.getContent())
                            .memoAt(memo.getMemoAt())
                            .memoType(memo.getMemoType())
                            .build()
            );
        }
        for(Memo memo : monthMemos) {
            monthMemoResponse.add(
                    MemoResponse.builder()
                            .memoId(memo.getMemoId())
                            .memoType(memo.getMemoType())
                            .memoAt(memo.getMemoAt())
                            .memo(memo.getContent())
                            .build()
            );
        }

        return MemoResponses.builder()
                .todayMemo(todayMemoResponse)
                .weekMemo(weekMemoResponse)
                .monthMemo(monthMemoResponse)
                .build();
    }

    @Override
    public void updateMemo(String token, Long memoId, UpdateMemoRequest updateMemoRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Memo memo = memoRepository.findByMemoId(memoId)
                .orElseThrow(RuntimeException::new);

        if(!memo.getEmail().equals(user.getEmail()))
            throw new RuntimeException("not my memo");

        memoRepository.save(
                memo.updateMemo(updateMemoRequest.getUpdateMsg())
        );
    }

    @Override
    @Transactional
    public void deleteMemo(String token, Long memoId) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(RuntimeException::new);

        Memo memo = memoRepository.findByMemoId(memoId)
                .orElseThrow(RuntimeException::new);

        if(!user.getEmail().equals(memo.getEmail()))
            throw new RuntimeException("not my memo");

        memoRepository.deleteByMemoId(memoId);
    }
}
