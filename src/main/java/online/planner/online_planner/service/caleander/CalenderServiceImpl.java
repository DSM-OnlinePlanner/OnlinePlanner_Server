package online.planner.online_planner.service.caleander;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.planner.Planner;
import online.planner.online_planner.entity.planner.repository.PlannerRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.error.exceptions.UserNotFoundException;
import online.planner.online_planner.payload.response.CalenderResponse;
import online.planner.online_planner.util.ConverterPlanner;
import online.planner.online_planner.util.JwtProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalenderServiceImpl implements CalendarService {

    private final UserRepository userRepository;
    private final PlannerRepository plannerRepository;

    private final JwtProvider jwtProvider;
    private final ConverterPlanner converterPlanner;

    @Override
    public List<CalenderResponse> getCalenders(String token, LocalDate date) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        YearMonth yearMonth = YearMonth.from(date);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return plannerRepository
                .findAllByEmailAndStartDateGreaterThanEqualOrStartDateLessThanEqualAndEndDateGreaterThanEqualOrEndDateLessThanEqual(
                        user.getEmail(),
                        startDate,
                        endDate,
                        startDate,
                        endDate
                );
    }
}
