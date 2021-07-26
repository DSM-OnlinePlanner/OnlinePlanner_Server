package online.planner.online_planner.entity.memo.repository;

import online.planner.online_planner.entity.memo.Memo;
import online.planner.online_planner.entity.memo.enums.MemoType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    <T> List<T> findAllByEmailAndMemoTypeAndMemoAtOrderByMemoAtAsc(String email, MemoType memoType, LocalDate memoAt);
    <T> List<T> findAllByEmailAndMemoTypeAndMemoAtLessThanEqualAndMemoAtGreaterThanEqual(String email, MemoType memoType, LocalDate memoAt, LocalDate memoAt2);
    Optional<Memo> findByMemoIdAndEmail(long memoId, String email);
    void deleteByMemoIdAndEmail(long memoId, String email);
    int countByEmail(String email);
}
