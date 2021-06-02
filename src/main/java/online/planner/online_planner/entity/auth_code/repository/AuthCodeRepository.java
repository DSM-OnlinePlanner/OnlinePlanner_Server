package online.planner.online_planner.entity.auth_code.repository;

import online.planner.online_planner.entity.auth_code.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthCodeRepository extends JpaRepository<AuthCode, String> {
}
