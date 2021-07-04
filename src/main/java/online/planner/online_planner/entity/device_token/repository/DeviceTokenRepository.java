package online.planner.online_planner.entity.device_token.repository;

import online.planner.online_planner.entity.device_token.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, String> {
    boolean existsByDeviceTokenAndEmail(String deviceToken, String email);
    List<DeviceToken> findAllByEmail(String email);
}
