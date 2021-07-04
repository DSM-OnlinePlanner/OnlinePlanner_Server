package online.planner.online_planner.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String email;

    private String password;

    private String nickName;

    private Integer saveDate;

    public User updateNickName(String nickName) {
        this.nickName = nickName;

        return this;
    }
}
