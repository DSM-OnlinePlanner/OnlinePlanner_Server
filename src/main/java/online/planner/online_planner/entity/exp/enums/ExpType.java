package online.planner.online_planner.entity.exp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpType {
    PLANNER(20), ROUTINE(10);

    private Integer giveExp;
}
