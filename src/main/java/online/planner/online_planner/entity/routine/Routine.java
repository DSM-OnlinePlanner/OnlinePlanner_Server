package online.planner.online_planner.entity.routine;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import online.planner.online_planner.entity.exp.enums.ExpType;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    private ExpType expType;

    private Boolean isSucceed;

    private Boolean isPushed;

    public Routine updateSucceed() {
        this.isSucceed = !isSucceed;

        return this;
    }

    public Routine updatePushed() {
        this.isPushed = !isPushed;

        return this;
    }
}
