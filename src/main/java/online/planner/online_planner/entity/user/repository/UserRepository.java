package online.planner.online_planner.entity.user.repository;

import online.planner.online_planner.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
