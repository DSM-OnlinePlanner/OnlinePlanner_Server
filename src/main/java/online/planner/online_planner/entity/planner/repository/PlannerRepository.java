package online.planner.online_planner.entity.planner.repository;

import online.planner.online_planner.entity.planner.Planner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Page<Planner> findAllByEmailAndStartDateAfterAndEndDateBeforeOrderByStartTimeAsc(String email, LocalDate startDate, LocalDate endDate, Pageable pageable);
    void deleteByPlannerId(long plannerId);
    Optional<Planner> findByPlannerId(Long plannerId);
    int countAllByIsSuccessAndEmail(Boolean isSuccess, String email);
    List<Planner> findAllByIsSuccessAndStartTimeAfterAndEndTimeBefore(Boolean isSuccess, LocalTime startTime, LocalTime endTime);
    int countDistinctByEmailAndIsSuccessAndStartTimeAfterAndEndTimeBefore(String email, Boolean isSuccess, LocalTime startTime, LocalTime endTime);
}
