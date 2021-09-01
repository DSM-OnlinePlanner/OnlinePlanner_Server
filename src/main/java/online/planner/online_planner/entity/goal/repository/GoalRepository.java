package online.planner.online_planner.entity.goal.repository;

import online.planner.online_planner.entity.goal.Goal;
import online.planner.online_planner.entity.goal.enums.GoalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findByGoalIdAndEmail(Long goalId, String email);
    <T> List<T> findAllByEmailAndGoalTypeAndGoalDateGreaterThanEqualAndGoalDateLessThanEqualOrderByGoalDateAsc(String email, GoalType goalType, LocalDate start, LocalDate end);
    void deleteByGoalIdAndEmail(Long goalId, String email);
    int countByEmail(String email);

    void deleteAllByEmail(String email);
}
