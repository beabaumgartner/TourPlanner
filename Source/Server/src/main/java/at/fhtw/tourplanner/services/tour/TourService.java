package at.fhtw.tourplanner.services.tour;

import at.fhtw.tourplanner.exceptions.BadRequestException;
import at.fhtw.tourplanner.exceptions.NoContentException;
import at.fhtw.tourplanner.exceptions.NotFoundException;
import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.models.TourListEntry;
import at.fhtw.tourplanner.services.tourLog.TourLogController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tours")
public class TourService {
    private final TourController tourController;
    private final TourLogController tourLogController;
    private static final Logger logger = LogManager.getLogger(TourService.class);
    public TourService(TourController tourController, TourLogController tourLogController) {
        this.tourController = tourController;
        this.tourLogController = tourLogController;
    }
    @PostMapping
    public ResponseEntity<Tour> createTour(@RequestBody Tour tour) {
        try {
            tour = tourController.createTour(tour);
            logger.info("TourService.createTour() - tour created successfully: " + tour);
            return new ResponseEntity<>(tour, HttpStatus.CREATED);
        }
        catch(BadRequestException e) {
            logger.warn("TourService.createTour() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch(Exception e) {
            logger.error("TourService.createTour() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping
    public ResponseEntity<List<TourListEntry>> getTourList() {
        try {
            List<TourListEntry> tourListEntries = tourController.getTourList();
            logger.info("TourService.getTourList() - tourListEntries retrieved successfully: " + tourListEntries);
            return new ResponseEntity<>(tourListEntries, HttpStatus.OK);
        }
        catch(NoContentException e) {
            logger.info("TourService.getTourList() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e) {
            logger.error("TourService.getTourList() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/{tourId}")
    public ResponseEntity<Tour> getTour(@PathVariable("tourId") Integer tourId) {
        try {
            Tour tour = tourController.getTour(tourId);
            logger.info("TourService.getTour() - tour retrieved successfully: " + tour);
            return new ResponseEntity<>(tour, HttpStatus.OK);
        }
        catch(NotFoundException e) {
            logger.info("TourService.getTour() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            logger.error("TourService.getTour() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/{tourId}")
    public ResponseEntity<Tour> updateTour(@PathVariable("tourId") Integer tourId, @RequestBody Tour tour) {
        try {
            tour = tourController.updateTour(tourId, tour);
            logger.info("TourService.updateTour() - tour updated successfully: " + tour);
            return new ResponseEntity<>(tour, HttpStatus.OK);
        }
        catch(NotFoundException e) {
            logger.info("TourService.updateTour() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(BadRequestException e) {
            logger.warn("TourService.updateTour() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch(Exception e) {
            logger.error("TourService.updateTour() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/{tourId}")
    public ResponseEntity<Tour> deleteTour(@PathVariable("tourId") Integer tourId) {
        try {
            tourController.deleteTour(tourId);
            logger.info("TourService.deleteTour() - tour deleted successfully: " + tourId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(NotFoundException e) {
            logger.info("TourService.deleteTour() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            logger.error("TourService.deleteTour() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<TourListEntry>> searchTour(@PathVariable("keyword") String keyword) {
        try {
            List<TourListEntry> tourListEntries = tourController.searchTour(keyword);
            logger.info("TourService.searchTour() - tourListEntries retrieved successfully: " + tourListEntries);
            return new ResponseEntity<>(tourListEntries, HttpStatus.OK);
        }
        catch(NoContentException e) {
            logger.info("TourService.searchTour() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(NotFoundException e) {
            logger.info("TourService.searchTour() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            logger.error("TourService.searchTour() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
