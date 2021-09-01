package online.planner.online_planner.entity.user.repository;

import online.planner.online_planner.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    void deleteAllByEmail(String email);
}
