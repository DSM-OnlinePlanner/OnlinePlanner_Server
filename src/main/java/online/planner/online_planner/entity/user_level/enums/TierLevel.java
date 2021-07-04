package online.planner.online_planner.entity.user_level.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TierLevel {
    ZERO_LV(40, "A4 용지",0 , 9),
    TEM_LV(60, "무료 플래너", 10, 19),
    TWENTY_LV(80, "스프링 노트 플래너", 20, 29),
    THIRTY_LV(100, "플라스틱 커버 플래너", 30, 39),
    FORTY_LV(120, "가죽 슬러브 플래너", 40, 59),
    SIXTY_LV(140, "고급 가죽 슬러브 플래너", 60, 79),
    EIGHTY_LV(160, "맞춤 재작 플래너", 80, 99),
    HUNDRED_LV(1000, "최고의 플러너", 100, 100);

    private Integer max_exp;
    private String tier;
    private Integer startLevel;
    private Integer endLevel;
}
