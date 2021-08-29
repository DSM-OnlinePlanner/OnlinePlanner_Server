package online.planner.online_planner.entity.device_token.repository;

import online.planner.online_planner.entity.device_token.DeviceToken;
import online.planner.online_planner.entity.device_token.DeviceTokenId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, DeviceTokenId> {
    boolean existsByDeviceTokenAndEmail(String deviceToken, String email);
    Optional<DeviceToken> findByDeviceToken(String deviceToken);
    void deleteByEmailAndDeviceToken(String email, String deviceToken);
    Optional<DeviceToken> findByEmailAndDeviceToken(String email, String deviceToken);

    @Query("select d.deviceToken from DeviceToken d where d.email = ?1")
    List<String> findAllDeviceTokenByEmail(String email);
}
