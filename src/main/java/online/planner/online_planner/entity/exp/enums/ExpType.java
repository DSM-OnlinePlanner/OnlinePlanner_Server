package online.planner.online_planner.entity.exp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpType {
    PLANNER(20),
    ROUTINE(10),
    PLANNER_10(20),
    ROUTINE_10(10),
    PLANNER_100(120),
    ROUTINE_100(110),
    PLANNER_1000(1200),
    ROUTINE_1000(1100),
    SUCCEED_PLANNER(20),
    SUCCEED_ROUTINE(10),
    SUCCEED_PLANNER_10(40),
    SUCCEED_ROUTINE_10(30),
    SUCCEED_PLANNER_100(140),
    SUCCEED_ROUTINE_100(130),
    SUCCEED_PLANNER_1000(1400),
    SUCCEED_ROUTINE_1000(1300),
    MEMO(10),
    FIRST_PLANNER(10),
    FIRST_ROUTINE(10),
    FIRST_MEMO(5),
    FIRST_GOAL(5),
    GOAL(10);

    private Integer giveExp;
}
