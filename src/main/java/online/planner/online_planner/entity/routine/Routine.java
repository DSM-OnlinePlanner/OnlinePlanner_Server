package online.planner.online_planner.entity.routine;

import lombok.*;
import online.planner.online_planner.entity.exp.enums.ExpType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long routineId;

    private String email;

    private String title;

    private String content;

    private LocalTime startTime;

    private LocalTime endTime;

    private ExpType expType;

    private Boolean isSucceed;

    private Boolean isPushed;
}
