package online.planner.online_planner.entity.goal.repository;

import online.planner.online_planner.entity.goal.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, String> {
}
