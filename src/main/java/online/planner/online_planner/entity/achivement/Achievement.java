package online.planner.online_planner.entity.achivement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.planner.online_planner.entity.achivement.enums.Achieve;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long achieveId;

    private String email;

    private Achieve achieve;

    private Boolean isSucceed;

    private LocalDate achieveDate;

    private LocalTime achieveAt;

    public Achievement succeed() {
        this.isSucceed = !isSucceed;

        return this;
    }
}
