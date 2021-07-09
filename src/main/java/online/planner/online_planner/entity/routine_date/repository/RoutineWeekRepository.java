package online.planner.online_planner.entity.routine_date.repository;

import online.planner.online_planner.entity.routine_date.RoutineWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineWeekRepository extends JpaRepository<RoutineWeek, Long> {
    boolean existsByRoutineIdAndDayOfWeek(long routineId, int dayOfWeek);
    List<RoutineWeek> findAllByRoutineId(long routineId);
    void deleteAllByRoutineId(long routineId);
}
