package online.planner.online_planner.entity.memo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.planner.online_planner.entity.memo.enums.MemoType;

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
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memoId;

    private String email;

    private String content;

    private MemoType memoType;

    private LocalDate memoAt;

    public Memo updateMemo(String content) {
        this.content = content;

        return this;
    }
}
