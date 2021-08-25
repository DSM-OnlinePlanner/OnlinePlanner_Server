package online.planner.online_planner.entity.routine_date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.planner.online_planner.entity.routine.Routine;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoutineWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer routineWeekId;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    private Routine routine;

    private long routineId;

    private int dayOfWeek;
}
