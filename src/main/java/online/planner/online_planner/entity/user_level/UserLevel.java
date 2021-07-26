package online.planner.online_planner.entity.user_level;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.planner.online_planner.entity.user_level.enums.TierLevel;

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

    private TierLevel tierLevel;

    private Integer userLv;

    public void levelUp(Integer userExp, Integer userLv) {
        this.userLv = userLv;
        this.userExp = userExp;
    }

    public UserLevel updateTier(TierLevel tierLevel) {
        this.tierLevel = tierLevel;

        return this;
    }

    public void plusExp(Integer userExp) {
        this.userExp = userExp;
    }
}
