package online.planner.online_planner.entity.planner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.planner.enums.Priority;

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
public class Planner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long plannerId;

    private String email;

    private String title;

    private String content;

    private Priority priority;

    private Boolean isSuccess;

    private ExpType expType;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer startTime;

    private Integer endTime;
}
