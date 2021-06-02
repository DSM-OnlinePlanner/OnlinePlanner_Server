package online.planner.online_planner.entity.planner.repository;

import online.planner.online_planner.entity.planner.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
}
