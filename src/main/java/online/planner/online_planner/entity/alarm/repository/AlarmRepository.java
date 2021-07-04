package online.planner.online_planner.entity.alarm.repository;

import online.planner.online_planner.entity.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
}
