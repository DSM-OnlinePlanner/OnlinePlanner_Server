package online.planner.online_planner.entity.device_token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Builder
@Entity
@IdClass(DeviceTokenId.class)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceToken {
    @Id
    private String email;

    @Id
    private String deviceToken;
}
