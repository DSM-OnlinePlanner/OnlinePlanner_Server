package online.planner.online_planner.entity.routine.repository;

import online.planner.online_planner.entity.routine.Routine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    Page<Routine> findAllByEmailAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualOrderByStartTimeAsc(String email, LocalTime startTime, LocalTime endTime, Pageable pageable);
    Optional<Routine> findByRoutineIdAndEmail(long routineId, String email);
    void deleteByRoutineId(long routineId);
    int countByEmail(String email);
    int countByIsSucceedAndEmail(Boolean isSucceed, String email);
    int countByEmailAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(String email, LocalDate writeAt, LocalDate writeAt2);
    int countByEmailAndIsSucceedAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(String email, Boolean isSuccess, LocalDate writeAt, LocalDate writeAt2);
    int countByEmailAndIsSucceedAndWriteAt(String email, Boolean isSucceed, LocalDate writeAt);

    List<Routine> findAllByIsPushed(Boolean isPushed);
}
