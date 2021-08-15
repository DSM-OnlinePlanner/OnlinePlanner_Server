package online.planner.online_planner.service.achievement;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.achivement.repository.AchievementRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.error.exceptions.UserNotFoundException;
import online.planner.online_planner.payload.request.ReadAchieveRequest;
import online.planner.online_planner.payload.response.AchievementResponse;
import online.planner.online_planner.util.JwtProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;

    private final JwtProvider jwtProvider;

    @Override
    public List<AchievementResponse> getAchievement(String token, ReadAchieveRequest achieveRequest) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        return achievementRepository.findByEmailAndIsSucceed(user.getEmail(), achieveRequest.isSucceed());
    }
}
