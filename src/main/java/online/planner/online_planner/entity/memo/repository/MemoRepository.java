package online.planner.online_planner.entity.memo.repository;

import online.planner.online_planner.entity.memo.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
