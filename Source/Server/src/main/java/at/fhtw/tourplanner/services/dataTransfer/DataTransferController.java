package at.fhtw.tourplanner.services.dataTransfer;

import at.fhtw.tourplanner.exceptions.NoContentException;
import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.models.TourLog;
import at.fhtw.tourplanner.repositories.TourLogRepository;
import at.fhtw.tourplanner.repositories.TourRepository;
import at.fhtw.tourplanner.services.tour.TourController;
import at.fhtw.tourplanner.services.tourLog.TourLogController;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataTransferController {
    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;
    private final TourController tourController;
    private final TourLogController tourLogController;
    public DataTransferController(TourController tourController, TourRepository tourRepository, TourLogRepository tourLogRepository, TourLogController tourLogController) {
        this.tourRepository = tourRepository;
        this.tourLogRepository = tourLogRepository;
        this.tourController = tourController;
        this.tourLogController = tourLogController;
    }
    public List<Tour> exportTours() {
        List<Tour> tours = tourRepository.findAll();
        for(Tour tour : tours) {
            tour.updateTourStats();
        }
        if(tours.isEmpty()) {
            throw new NoContentException("DataTransferController.exportTours() - no content");
        }
        return tours;
    }
    public List<Tour> importTours(List<Tour> tours) {
        // TODO: save the actual date for the tourLog
        List<Tour> createdTours = new ArrayList<>();
        for(Tour tour : tours) {
            Tour createdTour = tourController.createTour(tour);
            List<TourLog> createdTourLogs = new ArrayList<>();
            for(TourLog tourLog : tour.getTourLogs()) {
                TourLog createdTourLog = tourLogController.createTourLog(createdTour.getTourId(), tourLog);
                createdTourLogs.add(createdTourLog);
            }
            createdTours.add(createdTour);
        }
        return createdTours;
    }
}
