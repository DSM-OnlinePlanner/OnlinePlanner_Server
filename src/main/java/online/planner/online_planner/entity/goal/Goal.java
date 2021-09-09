package online.planner.online_planner.entity.goal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.goal.enums.GoalType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goalId;

    private String email;

    private String goal;

    private GoalType goalType;

    private LocalDate goalDate;

    private boolean isAchieve;

    private ExpType expType;

    public Goal updateGoal(String goal) {
        this.goal = goal;

        return this;
    }

    public Goal updateAchieve() {
        this.isAchieve = !isAchieve;

        return this;
    }
}
