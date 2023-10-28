package at.fhtw.tourplanner.repositories;

import at.fhtw.tourplanner.models.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TourLogRepository extends JpaRepository<TourLog, Integer> {
    @Transactional
    Integer deleteByTourLogId(Integer id);
}
