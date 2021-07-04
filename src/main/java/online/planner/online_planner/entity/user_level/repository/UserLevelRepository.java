package online.planner.online_planner.entity.user_level.repository;

import online.planner.online_planner.entity.user_level.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLevelRepository extends JpaRepository<UserLevel, Long> {
    Optional<UserLevel> findByEmail(String email);
}
