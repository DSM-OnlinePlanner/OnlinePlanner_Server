package online.planner.online_planner.entity.planner.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Want {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    private Integer want;
}
