package at.fhtw.tourplanner.services.tourLog;

import at.fhtw.tourplanner.exceptions.NoContentException;
import at.fhtw.tourplanner.exceptions.NotFoundException;
import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.models.TourLog;
import at.fhtw.tourplanner.repositories.TourLogRepository;
import at.fhtw.tourplanner.repositories.TourRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class TourLogController {
    private final TourLogRepository tourLogRepository;
    private final TourRepository tourRepository;
    public TourLogController(TourLogRepository tourLogRepository, TourRepository tourRepository) {
        this.tourLogRepository = tourLogRepository;
        this.tourRepository = tourRepository;
    }
    public TourLog createTourLog(Integer tourId, TourLog tourLog) {
        try {
            Tour tour = tourRepository.findById(tourId).get();
            tourLog.setTour(tour);
            tourLog = tourLogRepository.save(tourLog);
            return tourLog;
        }
        catch(NoSuchElementException e) {
            throw new NotFoundException("TourLogController.createTourLog() - tourId not found: " + tourId);
        }
    }
    public List<TourLog> getTourLogs(Integer tourId) {
        try {
            Tour tour = tourRepository.findById(tourId).get();
            List<TourLog> tourLogs = tour.getTourLogs();
            if(tourLogs.isEmpty()) {
                throw new NoContentException("TourLogController.getTourLogs() - no content");
            }
            return tourLogs;
        }
        catch(NoSuchElementException e) {
            throw new NotFoundException("TourLogController.getTourLogs() - tourId not found: " + tourId);
        }
    }
    public List<TourLog> getTourLogs() {
        List<TourLog> tourLogs = tourLogRepository.findAll();
        if(tourLogs.isEmpty()) {
            throw new NoContentException("TourLogController.getTourLogs() - no content");
        }
        return tourLogs;
    }
    public TourLog getTourLog(Integer tourLogId) {
        try {
            TourLog tourLog = tourLogRepository.findById(tourLogId).get();
            return tourLog;
        }
        catch(NoSuchElementException e) {
            throw new NotFoundException("TourLogController.getTourLog() - tourLogId not found: " + tourLogId);
        }
    }
    public TourLog updateTourLog(Integer tourLogId, TourLog tourLog) {
        try {
            TourLog updatedTourLog = tourLogRepository.findById(tourLogId).get();

            if(tourLog.getDate() != null) {
                updatedTourLog.setDate(tourLog.getDate());
            }
            if(tourLog.getComment() != null) {
                updatedTourLog.setComment(tourLog.getComment());
            }
            if(tourLog.getDifficulty() != null) {
                updatedTourLog.setDifficulty(tourLog.getDifficulty());
            }
            if(tourLog.getTotalTime() != null) {
                updatedTourLog.setTotalTime(tourLog.getTotalTime());
            }
            if(tourLog.getRating() != null) {
                updatedTourLog.setRating(tourLog.getRating());
            }
            updatedTourLog = tourLogRepository.save(updatedTourLog);

            return updatedTourLog;
        }
        catch(NoSuchElementException e) {
            throw new NotFoundException("TourLogController.updateTourLog() - tourLogId not found: " + tourLogId);
        }
    }
    public void deleteTourLog(Integer tourLogId) {
        Integer deletedRows = tourLogRepository.deleteByTourLogId(tourLogId);
        if(deletedRows < 1) {
            throw new NotFoundException("TourLogController.deleteTourLog() - tourLogId not found: " + tourLogId);
        }
    }
}
