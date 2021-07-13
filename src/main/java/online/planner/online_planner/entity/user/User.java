package online.planner.online_planner.entity.user;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
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

    public User updatePassword(String password) {
        this.password = password;

        return this;
    }
}
