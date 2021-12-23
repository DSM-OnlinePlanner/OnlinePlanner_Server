package online.planner.online_planner.entity.planner;

import lombok.*;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.exp.enums.ExpTypeConvertor;
import online.planner.online_planner.entity.planner.enums.Priority;
import online.planner.online_planner.entity.planner.enums.Want;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Planner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long plannerId;

    private String email;

    private String title;

    private String content;

    private Priority priority;

    private Want want;

    private Boolean isSuccess;

    @Convert(converter = ExpTypeConvertor.class)
    private ExpType expType;

    private LocalDate startDate;

    private LocalDate endDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    private Boolean isPushed;

    private Boolean isPushSuccess;

    private Boolean isFailed;

    private LocalDate writeAt;

    public Planner checkSuccess() {
        this.isSuccess = true;

        return this;
    }

    public Planner updatePush() {
        this.isPushed = !isPushed;

        return this;
    }

    public Planner updatePriority(Priority priority, Want want) {
        this.priority = priority;
        this.want = want;

        return this;
    }

    public Planner updateFailed() {
        this.isFailed = true;

        return this;
    }
}
