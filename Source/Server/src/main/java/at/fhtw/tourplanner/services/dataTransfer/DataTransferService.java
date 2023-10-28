package at.fhtw.tourplanner.services.dataTransfer;

import at.fhtw.tourplanner.exceptions.BadRequestException;
import at.fhtw.tourplanner.exceptions.NoContentException;
import at.fhtw.tourplanner.models.Tour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataTransferService {
    private final DataTransferController dataTransferController;
    private static final Logger logger = LogManager.getLogger(DataTransferService.class);
    public DataTransferService(DataTransferController dataTransferController) {
        this.dataTransferController = dataTransferController;
    }
    @RequestMapping("/export")
    @GetMapping
    public ResponseEntity<List<Tour>> exportTours() {
        try {
            List<Tour> tours = dataTransferController.exportTours();
            logger.info("DataTransferService.exportTours() - tours exported successfully: " + tours);
            return new ResponseEntity<>(tours, HttpStatus.OK);
        }
        catch(NoContentException e) {
            logger.info("DataTransferService.exportTours() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e) {
            logger.error("DataTransferService.exportTours() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping("/import")
    @PostMapping
    public ResponseEntity<List<Tour>> importTours(@RequestBody List<Tour> tours) {
        try {
            List<Tour> importedTours = dataTransferController.importTours(tours);
            logger.info("DataTransferService.importTours() - tours imported successfully: " + tours);
            return new ResponseEntity<>(importedTours, HttpStatus.CREATED);
        }
        catch(BadRequestException e) {
            logger.warn("DataTransferService.importTours() - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch(Exception e) {
            logger.error("DataTransferService.importTours() - " + e.getMessage());
            // e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
