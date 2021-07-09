package online.planner.online_planner.entity.routine.repository;

import online.planner.online_planner.entity.routine.Routine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    Page<Routine> findAllByEmailAndStartTimeAfterAndEndTimeBeforeOrderByStartTimeDesc(String email, LocalTime startTime, LocalTime endTime, Pageable pageable);
    Optional<Routine> findByRoutineId(Long routineId);
    void deleteByRoutineId(long routineId);
}
