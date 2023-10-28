package at.fhtw.tourplanner.repositories;

import at.fhtw.tourplanner.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TourRepository extends JpaRepository<Tour, Integer> {
    @Transactional
    Integer deleteByTourId(Integer id);
}
