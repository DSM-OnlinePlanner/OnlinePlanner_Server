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
    <T> Page<T> findAllByEmailAndTitleContainingOrderByStartDateAsc(String email, String title, Pageable pageable);
    void deleteByPlannerIdAndEmail(long plannerId, String email);
    Optional<Planner> findByPlannerIdAndEmail(Long plannerId, String email);
    <T> List<T> findAllByEmailAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String email, LocalDate startDate, LocalDate endDate);
    int countByIsSuccessAndEmail(Boolean isSuccess, String email);
    int countByEmail(String email);
    int countByEmailAndTitleContaining(String email, String title);
    int countByEmailAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(String email, LocalDate writeAt, LocalDate writeAt2);
    int countByEmailAndIsSuccessAndWriteAtGreaterThanEqualAndWriteAtLessThanEqual(String email, Boolean isSuccess, LocalDate writeAt, LocalDate writeAt2);
    int countByEmailAndIsSuccessAndStartDateLessThanEqualAndEndDateGreaterThanEqual(String email, Boolean isSuccess, LocalDate startDate, LocalDate endDate);
    int countByEmailAndIsSuccessAndWriteAt(String email, Boolean isSuccess, LocalDate writeAt);
    int countByEmailAndWriteAt(String email, LocalDate writeAt);
    int countDistinctByEmailAndStartDateGreaterThanEqualOrEmailAndEndDateLessThanEqual(String email, LocalDate startDate, String email2, LocalDate endDate);
    int countDistinctByIsSuccessAndEmailAndStartDateGreaterThanEqualOrEmailAndEndDateLessThanEqual(Boolean isSuccess, String email, LocalDate startDate, String email2, LocalDate endDate);

    void deleteAllByEmail(String email);
    List<Planner> findAllByIsPushedAndStartDateGreaterThanEqualAndEndDateLessThanEqual(Boolean isPushed, LocalDate startDate, LocalDate endDate);
}
