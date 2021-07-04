package online.planner.online_planner.entity.auth_code.repository;

import online.planner.online_planner.entity.auth_code.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthCodeRepository extends JpaRepository<AuthCode, String> {
    Optional<AuthCode> findByCodeAndEmail(String code, String email);
    void deleteByCodeAndEmail(String code, String email);
    Optional<AuthCode> findByEmail(String email);
}
