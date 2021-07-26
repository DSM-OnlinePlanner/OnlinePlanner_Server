package online.planner.online_planner.entity.achivement.repository;

import online.planner.online_planner.entity.achivement.Achievement;
import online.planner.online_planner.entity.achivement.enums.Achieve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    boolean existsByEmailAndIsSucceedAndAchieve(String email, boolean succeed, Achieve achieve);
    Optional<Achievement> findByEmailAndAchieve(String email, Achieve achieve);
    <T> List<T> findByEmailAndIsSucceed(String email, boolean succeed);
}
