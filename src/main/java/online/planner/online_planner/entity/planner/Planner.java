package online.planner.online_planner.entity.planner;

import lombok.*;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.exp.enums.ExpTypeConvertor;
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

    private Integer priority;

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

    public Planner checkSuccess() {
        this.isSuccess = !isSuccess;

        return this;
    }

    public Planner updatePush() {
        this.isPushed = !isPushed;

        return this;
    }
}
