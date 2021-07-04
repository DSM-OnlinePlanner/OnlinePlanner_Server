package online.planner.online_planner.entity.planner.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Priority {
    A(0), B(5), C(10), D(15), E(20);

    private Integer priority;
}
