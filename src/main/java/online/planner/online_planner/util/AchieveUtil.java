package online.planner.online_planner.util;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.achivement.Achievement;
import online.planner.online_planner.entity.achivement.enums.Achieve;
import online.planner.online_planner.entity.achivement.repository.AchievementRepository;
import online.planner.online_planner.entity.exp.enums.ExpType;
import online.planner.online_planner.entity.user_level.UserLevel;
import online.planner.online_planner.error.exceptions.AchievementNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AchieveUtil {

    private final AchievementRepository achievementRepository;

    private final UserLevelUtil userLevelUtil;

    @Async
    public void achieveManagement(UserLevel user, Achieve achieve) {
        Achievement achievement = achievementRepository.findByEmailAndAchieve(user.getEmail(), achieve)
                .orElseThrow(AchievementNotFoundException::new);

        achievementRepository.save(
                achievement.succeed()
        );
    }

    @Async
    public void achievePlannerNum(int routineNum, UserLevel user, boolean isAll, boolean isRoutine) {
        if(isAll) {
            if (routineNum == 10 && achievementRepository.existsByEmailAndIsSucceedAndAchieve(user.getEmail(), false, Achieve.ROUTINE_10)) {
                if(isRoutine) {
                    achieveManagement(user, Achieve.ROUTINE_10);
                    userLevelUtil.userLevelManagement(user, ExpType.ROUTINE_10);
                }else {
                    achieveManagement(user, Achieve.PLANNER_10);
                    userLevelUtil.userLevelManagement(user, ExpType.PLANNER_10);
                }
            } else if (routineNum == 100 && achievementRepository.existsByEmailAndIsSucceedAndAchieve(user.getEmail(), false, Achieve.ROUTINE_100)) {
                if(isRoutine) {
                    achieveManagement(user, Achieve.ROUTINE_100);
                    userLevelUtil.userLevelManagement(user, ExpType.ROUTINE_100);
                }else {
                    achieveManagement(user, Achieve.PLANNER_100);
                    userLevelUtil.userLevelManagement(user, ExpType.PLANNER_100);
                }
            } else if (routineNum == 1000 && achievementRepository.existsByEmailAndIsSucceedAndAchieve(user.getEmail(), false, Achieve.PLANNER_1000)) {
                if(isRoutine) {
                    achieveManagement(user, Achieve.ROUTINE_1000);
                    userLevelUtil.userLevelManagement(user, ExpType.ROUTINE_1000);
                }else {
                    achieveManagement(user, Achieve.PLANNER_1000);
                    userLevelUtil.userLevelManagement(user, ExpType.PLANNER_1000);
                }
            }
        } else {
            if(routineNum == 1 && achievementRepository.existsByEmailAndIsSucceedAndAchieve(user.getEmail(), false, Achieve.SUCCEED_ROUTINE)) {
                if(isRoutine) {
                    achieveManagement(user, Achieve.SUCCEED_ROUTINE);
                    userLevelUtil.userLevelManagement(user, ExpType.SUCCEED_ROUTINE);
                }else {
                    achieveManagement(user, Achieve.SUCCEED_PLANNER);
                    userLevelUtil.userLevelManagement(user, ExpType.SUCCEED_PLANNER);
                }
            } else if(routineNum == 10 && achievementRepository.existsByEmailAndIsSucceedAndAchieve(user.getEmail(), false, Achieve.SUCCEED_ROUTINE_10)) {
                if(isRoutine) {
                    achieveManagement(user, Achieve.SUCCEED_ROUTINE_10);
                    userLevelUtil.userLevelManagement(user, ExpType.SUCCEED_ROUTINE_10);
                }else {
                    achieveManagement(user, Achieve.SUCCEED_PLANNER_10);
                    userLevelUtil.userLevelManagement(user, ExpType.SUCCEED_PLANNER_10);
                }
            } else if(routineNum == 100 && achievementRepository.existsByEmailAndIsSucceedAndAchieve(user.getEmail(), false, Achieve.SUCCEED_ROUTINE_100)) {
                if(isRoutine) {
                    achieveManagement(user, Achieve.SUCCEED_ROUTINE_100);
                    userLevelUtil.userLevelManagement(user, ExpType.SUCCEED_ROUTINE_100);
                }else {
                    achieveManagement(user, Achieve.SUCCEED_PLANNER_100);
                    userLevelUtil.userLevelManagement(user, ExpType.SUCCEED_PLANNER_100);
                }
            } else if(routineNum == 1000 && achievementRepository.existsByEmailAndIsSucceedAndAchieve(user.getEmail(), false, Achieve.SUCCEED_ROUTINE_1000)) {
                if(isRoutine) {
                    achieveManagement(user, Achieve.SUCCEED_ROUTINE_1000);
                    userLevelUtil.userLevelManagement(user, ExpType.SUCCEED_ROUTINE_1000);
                }else {
                    achieveManagement(user, Achieve.SUCCEED_PLANNER_1000);
                    userLevelUtil.userLevelManagement(user, ExpType.SUCCEED_PLANNER_1000);
                }
            }

        }
    }

}
