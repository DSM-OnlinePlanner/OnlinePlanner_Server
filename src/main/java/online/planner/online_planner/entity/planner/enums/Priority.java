package online.planner.online_planner.entity.planner.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Priority {
    A1(1), A2(2), A3(3), A4(4), A5(5),
    B1(6), B2(7), B3(8), B4(9), B5(10),
    C1(1), C2(2), C3(3), C4(4), C5(5),
    D1(6), D2(7), D3(8), D4(9), D5(10),
    E1(1), E2(2), E3(3), E4(4), E5(5);

    private int priority;
}
