package online.planner.online_planner.util;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.achivement.Achievement;
import online.planner.online_planner.entity.achivement.enums.Achieve;
import online.planner.online_planner.entity.achivement.repository.AchievementRepository;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.user_level.UserLevel;
import online.planner.online_planner.entity.user_level.enums.TierLevel;
import online.planner.online_planner.entity.user_level.repository.UserLevelRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class UserLevelUtil {

    private final UserLevelRepository userLevelRepository;
    private final AchievementRepository achievementRepository;

    @Async
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

            Achievement achievement;

            if(level == 10 && achievementRepository.existsByEmailAndIsSucceedAndAchieve(userLevel.getEmail(), false, Achieve.LV_10)) {
                achievement = achievementRepository.findByEmailAndAchieve(userLevel.getEmail(), Achieve.LV_10)
                        .orElseThrow(RuntimeException::new);

                achievementRepository.save(
                        achievement.succeed()
                );
            }else if(level == 50 && achievementRepository.existsByEmailAndIsSucceedAndAchieve(userLevel.getEmail(), false, Achieve.LV_50)) {
                achievement = achievementRepository.findByEmailAndAchieve(userLevel.getEmail(), Achieve.LV_50)
                        .orElseThrow(RuntimeException::new);

                achievementRepository.save(
                        achievement.succeed()
                );
            }else if(level == 100 && achievementRepository.existsByEmailAndIsSucceedAndAchieve(userLevel.getEmail(), false, Achieve.LV_100)) {
                achievement = achievementRepository.findByEmailAndAchieve(userLevel.getEmail(), Achieve.LV_100)
                        .orElseThrow(RuntimeException::new);

                achievementRepository.save(
                        achievement.succeed()
                );
            }

            userLevel.levelUp(exp, level);
        }else {
            userLevel.plusExp(exp);
        }

        userLevelRepository.save(userLevel);
    }
}
