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
    <T> Page<T> findAllByEmailAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateAsc(String email, LocalDate startDate, LocalDate endDate, Pageable pageable);
    void deleteByPlannerIdAndEmail(long plannerId, String email);
    Optional<Planner> findByPlannerIdAndEmail(Long plannerId, String email);
    <T> List<T> findAllByEmailAndStartDateGreaterThanEqualOrStartDateLessThanEqualAndEndDateGreaterThanEqualOrEndDateLessThanEqual(String email, LocalDate startDate, LocalDate startDate2, LocalDate endDate, LocalDate endDate2);
    int countByIsSuccessAndEmail(Boolean isSuccess, String email);
    int countByEmail(String email);
    int countByEmailAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(String email, LocalDate writeAt, LocalDate writeAt2);
    int countByEmailAndIsSuccessAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(String email, Boolean isSuccess, LocalDate writeAt, LocalDate writeAt2);
    int countByEmailAndIsSuccessAndStartDateLessThanEqualAndEndDateGreaterThanEqual(String email, Boolean isSuccess, LocalDate startDate, LocalDate endDate);

    List<Planner> findAllByIsPushedAndStartDateGreaterThanEqualAndEndDateLessThanEqual(Boolean isPushed, LocalDate startDate, LocalDate endDate);
}
