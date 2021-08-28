package online.planner.online_planner.entity.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeId;

    private String email;

    private String title;

    private LocalDate noticeDate;

    private LocalTime noticemAt;

    private Boolean isSee;

    public Notice updateIsSee() {
        this.isSee = true;

        return this;
    }
}