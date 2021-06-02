package online.planner.online_planner.entity.exp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.planner.online_planner.entity.exp.enums.ExpType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EXP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long expId;

    private ExpType expType;

    private Integer giveEXP;
}
