package online.planner.online_planner.entity.device_token;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class DeviceTokenId implements Serializable {
    private String deviceToken;
    private String email;
}
