package online.planner.online_planner.entity.achivement.repository;

import online.planner.online_planner.entity.achivement.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, String> {
}
