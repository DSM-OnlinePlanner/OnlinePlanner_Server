package online.planner.online_planner.entity.max_level;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.planner.online_planner.entity.max_level.enums.LevelType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MaxLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long masLvId;

    private LevelType levelType;

    private Integer maxEXP;
}
