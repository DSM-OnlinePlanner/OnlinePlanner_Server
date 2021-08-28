package online.planner.online_planner.entity.routine.repository;

import online.planner.online_planner.entity.routine.Routine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    Page<Routine> findAllByEmailAndTitleContainingOrderByStartTimeAsc(String email, String title, Pageable pageable);
    Optional<Routine> findByRoutineIdAndEmail(long routineId, String email);
    void deleteByRoutineId(long routineId);
    int countByEmail(String email);
    int countByEmailAndTitleContaining(String email, String title);
    int countByIsSucceedAndEmail(Boolean isSucceed, String email);
    int countByEmailAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(String email, LocalDate writeAt, LocalDate writeAt2);
    int countByEmailAndIsSucceedAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(String email, Boolean isSuccess, LocalDate writeAt, LocalDate writeAt2);

    List<Routine> findAllByIsFailedOrIsSucceed(Boolean isFailed, Boolean isSucceed);

    List<Routine> findAllByIsPushed(Boolean isPushed);
    Optional<Routine> findByRoutineId(long routineId);
}
