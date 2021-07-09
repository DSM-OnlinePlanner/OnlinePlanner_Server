package online.planner.online_planner.util;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.user_level.UserLevel;
import online.planner.online_planner.entity.user_level.enums.TierLevel;
import online.planner.online_planner.entity.user_level.repository.UserLevelRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class UserLevelUtil {

    private final UserLevelRepository userLevelRepository;

    public void userLevelManagement(UserLevel userLevel, ExpType expType) {
        TierLevel tierLevel;
        int exp, level;

        exp = userLevel.getUserExp() + expType.getGiveExp();
        if(userLevel.getTierLevel().getMax_exp() < exp) {
            exp = 0;
            level = userLevel.getUserLv() + 1;
            if(level > userLevel.getTierLevel().getEndLevel()) {
                tierLevel = Stream.of(TierLevel.values())
                        .filter(tierLevel1 -> tierLevel1.getStartLevel().equals(level))
                        .findFirst()
                        .orElseThrow(RuntimeException::new);
                userLevelRepository.save(
                        userLevel.updateTier(tierLevel)
                );
            }
            userLevelRepository.save(
                    userLevel.levelUp(exp, level)
            );
        }
    }
}
