package online.planner.online_planner.entity.exp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpType {
    PLANNER(20), ROUTINE(10), PLANNER_10(20), ROUTINE_10(10),
    PLANNER_100(120), ROUTINE_100(110), WRITE(10);

    private Integer giveExp;
}
