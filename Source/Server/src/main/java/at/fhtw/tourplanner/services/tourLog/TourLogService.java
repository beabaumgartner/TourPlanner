package at.fhtw.tourplanner.services.tourLog;

import at.fhtw.tourplanner.exceptions.NoContentException;
import at.fhtw.tourplanner.exceptions.NotFoundException;
import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.models.TourLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TourLogService {
    private final TourLogController tourLogController;
    private static final Logger logger = LogManager.getLogger(TourLogService.class);
    public TourLogService(TourLogController tourLogController) {
        this.tourLogController = tourLogController;
    }
    @PostMapping("/tours/{tourId}/tourlogs")
    public ResponseEntity<TourLog> createTourLog(@PathVariable("tourId") Integer tourId, @RequestBody TourLog tourLog) {
        try {
            tourLog = tourLogController.createTourLog(tourId, tourLog);
            logger.info("TourLogService.createTourLog() - tourLog created successfully: " + tourLog);
            return new ResponseEntity<>(tourLog, HttpStatus.CREATED);
        }
        catch(NotFoundException e) {
            logger.info("TourLogService.createTourLog() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            logger.error("TourLogService.createTourLog() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/tours/{tourId}/tourlogs")
    public ResponseEntity<List<TourLog>> getTourLogs(@PathVariable("tourId") Integer tourId) {
        try {
            List<TourLog> tourLogs = tourLogController.getTourLogs(tourId);
            logger.info("TourLogService.getTourLogs() - tourLogs retrieved successfully: " + tourLogs);
            return new ResponseEntity<>(tourLogs, HttpStatus.OK);
        }
        catch(NoContentException e) {
            logger.info("TourLogService.getTourLogs() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(NotFoundException e) {
            logger.info("TourLogService.getTourLogs() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            logger.error("TourLogService.getTourLogs() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/tourlogs")
    public ResponseEntity<List<TourLog>> getTourLogs() {
        try {
            List<TourLog> tourLogs = tourLogController.getTourLogs();
            logger.info("TourLogService.getTourLogs() - tourLogs retrieved successfully: " + tourLogs);
            return new ResponseEntity<>(tourLogs, HttpStatus.OK);
        }
        catch(NoContentException e) {
            logger.info("TourLogService.getTourLogs() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e) {
            logger.error("TourLogService.getTourLogs() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/tourlogs/{tourLogId}")
    public ResponseEntity<TourLog> getTourLog(@PathVariable("tourLogId") Integer tourLogId) {
        try {
            TourLog tourLog = tourLogController.getTourLog(tourLogId);
            logger.info("TourLogService.getTourLog() - tourLog retrieved successfully: " + tourLog);
            return new ResponseEntity<>(tourLog, HttpStatus.OK);
        }
        catch(NotFoundException e) {
            logger.info("TourLogService.getTourLog() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            logger.error("TourLogService.getTourLog() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/tourlogs/{tourLogId}")
    public ResponseEntity<TourLog> updateTourLog(@PathVariable("tourLogId") Integer tourLogId, @RequestBody TourLog tourLog) {
        try {
            tourLog = tourLogController.updateTourLog(tourLogId, tourLog);
            logger.info("TourLogService.updateTourLog() - tourLog updated successfully: " + tourLog);
            return new ResponseEntity<>(tourLog, HttpStatus.OK);
        }
        catch(NotFoundException e) {
            logger.info("TourLogService.updateTourLog() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            logger.error("TourLogService.updateTourLog() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/tourlogs/{tourLogId}")
    public ResponseEntity<Tour> deleteTourLog(@PathVariable("tourLogId") Integer tourLogId) {
        try {
            tourLogController.deleteTourLog(tourLogId);
            logger.info("TourLogService.deleteTourLog() - tourLog deleted successfully: " + tourLogId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(NotFoundException e) {
            logger.info("TourLogService.deleteTourLog() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e) {
            logger.error("TourLogService.deleteTourLog() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
