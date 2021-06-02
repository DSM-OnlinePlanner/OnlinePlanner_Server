package online.planner.online_planner.entity.routine_date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RoutineWeek {
    @Id
    private long routineId;

    private int dayOfWeek;
}
