package online.planner.online_planner.entity.achivement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Achieve {
    PLANNER_10("할일 10개 작성하기"),
    ROUTINE_10("루틴 10개 작성하기"),
    PLANNER_100("할일 100개 작성하기"),
    ROUTINE_100("루틴 100개 작성하기"),
    PLANNER_1000("할일 1000개 작성하기"),
    ROUTINE_1000("루틴 1000개 작성하기"),
    SUCCEED_PLANNER("할일 1개 완료"),
    SUCCEED_ROUTINE("루틴 1개 완료"),
    SUCCEED_PLANNER_10("할일 10개 완료"),
    SUCCEED_ROUTINE_10("루틴 10개 완료"),
    SUCCEED_PLANNER_100("할일 100개 완료"),
    SUCCEED_ROUTINE_100("루틴 100개 완료"),
    SUCCEED_PLANNER_1000("할일 1000개 완료"),
    SUCCEED_ROUTINE_1000("루틴 1000개 완료"),
    FIRST_PLANNER("첫 플래너 작성하기"),
    FIRST_ROUTINE("첫 루틴 작성하기"),
    FIRST_MEMO("첫 메모 작성하기"),
    FIRST_GOAL("첫 목표 작성하기"),
    LV_10("10LV 달성하기"),
    LV_50("50LV 달성하기"),
    LV_100("100LV 달성하기");

    private String achieve;
}
