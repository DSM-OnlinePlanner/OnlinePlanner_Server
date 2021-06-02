package online.planner.online_planner.entity.user_level;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserLevel {
    @Id
    private String email;

    private Integer userExp;

    private Integer userLv;
}
