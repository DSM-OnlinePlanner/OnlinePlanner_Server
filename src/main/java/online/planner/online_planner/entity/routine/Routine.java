package online.planner.online_planner.entity.routine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long routineId;

    private String title;

    private String content;

    private Integer startTime;

    private Integer endTime;
}
