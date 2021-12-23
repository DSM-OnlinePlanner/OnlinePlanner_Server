package online.planner.online_planner.entity.routine;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.planner.enums.Priority;
import online.planner.online_planner.entity.routine_date.RoutineWeek;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    private Priority priority;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    private ExpType expType;

    private Boolean isSucceed;

    private Boolean isFailed;

    private Boolean isPushed;

    private Boolean isPushSuccess;

    private LocalDate writeAt;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.REMOVE)
    private List<RoutineWeek> routineWeeks;

    public Routine updateSucceed() {
        this.isSucceed = true;

        return this;
    }

    public Routine updatePushed() {
        this.isPushed = !isPushed;

        return this;
    }

    public Routine updatePriority(Priority priority) {
        this.priority = priority;

        return this;
    }

    public Routine updateFailed() {
        this.isFailed = true;

        return this;
    }
}
