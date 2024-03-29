package online.planner.online_planner.entity.routine_date.repository;

import online.planner.online_planner.entity.routine.Routine;
import online.planner.online_planner.entity.routine_date.RoutineWeek;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;

public interface RoutineWeekRepository extends JpaRepository<RoutineWeek, Long> {
    boolean existsByRoutine_RoutineIdAndDayOfWeek(long routineId, int dayOfWeek);
    <T> List<T> findAllByRoutine_RoutineId(long routineId);
    void deleteAllByRoutine_RoutineId(long routineId);
    List<RoutineWeek> findAllByDayOfWeekAndRoutine_StartTimeGreaterThanEqualAndRoutine_EndTimeLessThanEqual(int dayOfWeek, LocalTime routine_startTime, LocalTime routine_endTime);
    Page<RoutineWeek> findAllByRoutine_EmailAndDayOfWeek(String routine_email, int dayOfWeek, Pageable pageable);

    void deleteAllByRoutine(Routine routine);
}
