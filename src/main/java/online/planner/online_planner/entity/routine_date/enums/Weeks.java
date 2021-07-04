package online.planner.online_planner.entity.routine_date.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Weeks {
    월(1), 화(2), 수(3), 목(4), 금(5), 토(6), 일(7);

    private final Integer dayOfWeek;
}
