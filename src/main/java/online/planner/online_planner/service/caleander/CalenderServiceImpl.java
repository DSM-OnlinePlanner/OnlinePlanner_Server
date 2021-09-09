package online.planner.online_planner.service.caleander;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.entity.planner.Planner;
import online.planner.online_planner.entity.planner.repository.PlannerRepository;
import online.planner.online_planner.entity.user.User;
import online.planner.online_planner.entity.user.repository.UserRepository;
import online.planner.online_planner.error.exceptions.UserNotFoundException;
import online.planner.online_planner.payload.response.CalenderResponse;
import online.planner.online_planner.util.JwtProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalenderServiceImpl implements CalendarService {

    private final UserRepository userRepository;
    private final PlannerRepository plannerRepository;

    private final JwtProvider jwtProvider;

    @Override
    public List<CalenderResponse> getCalenders(String token, LocalDate date) {
        User user = userRepository.findByEmail(jwtProvider.getEmail(token))
                .orElseThrow(UserNotFoundException::new);

        YearMonth start = YearMonth.from(date.minusMonths(1));
        YearMonth end = YearMonth.from(date.plusMonths(1));
        LocalDate startDate = start.atDay(1);
        LocalDate endDate = end.atEndOfMonth();

        List<Planner> planners = plannerRepository
                .findAllByEmailAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
                        user.getEmail(),
                        startDate,
                        endDate
                );

        List<CalenderResponse> calenderResponses = new ArrayList<>();

        for(Planner planner : planners) {
            calenderResponses.add(
                    CalenderResponse.builder()
                            .startDate(LocalDateTime.of(planner.getStartDate(), planner.getStartTime()))
                            .endDate(LocalDateTime.of(planner.getEndDate(), planner.getEndTime()))
                            .priority(planner.getPriority())
                            .want(planner.getWant())
                            .title(planner.getTitle())
                            .isSucceed(planner.getIsSuccess())
                            .build()
            );
        }

        return calenderResponses;
    }
}
