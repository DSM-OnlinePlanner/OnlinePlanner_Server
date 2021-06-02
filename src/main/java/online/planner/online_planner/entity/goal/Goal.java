package online.planner.online_planner.entity.goal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.planner.online_planner.entity.goal.enums.GoalType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
    @Id
    private String email;

    private String goal;

    private GoalType goalType;

    private LocalDate endDate;
}
