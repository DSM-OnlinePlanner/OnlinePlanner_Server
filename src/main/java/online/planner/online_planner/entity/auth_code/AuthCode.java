package online.planner.online_planner.entity.auth_code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AuthCode {
    @Id
    private String email;

    private String code;
}
