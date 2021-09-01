package online.planner.online_planner.entity.notice.repository;

import online.planner.online_planner.entity.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    <T> Page<T> findAllByEmail(String email, Pageable pageable);
    void deleteByEmailAndNoticeId(String email, Long noticeId);
    Optional<Notice> findByNoticeIdAndEmail(Long noticeId, String email);
    boolean existsByEmailAndIsSee(String email, Boolean isSee);

    void deleteAllByEmail(String email);
}
