package at.fhtw.businessLogic;

import at.fhtw.exceptions.*;
import at.fhtw.models.Tour;
import at.fhtw.models.TourListEntry;
import at.fhtw.models.TourLog;
import at.fhtw.services.DataTransferService;
import at.fhtw.services.TourLogService;
import at.fhtw.services.TourService;
import at.fhtw.utils.ImageHandler;
import at.fhtw.utils.pdfFileHandler.PdfFileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BusinessLogic {
    private TourService tourService = new TourService();
    private TourLogService tourLogService = new TourLogService();
    private DataTransferService dataTransferService = new DataTransferService();
    private static final Logger logger = LogManager.getLogger(BusinessLogic.class);
    public void createTour(Tour tour) {
        try {
            tour = tourService.createTour(tour);
            ImageHandler.saveBase64EncodedImageToFile(tour.getTourInformation(), tour.getTourId().toString());
            logger.info("BusinessLogic.createTour() - tour created successfully: " + tour);
            // TODO: show created tour in ui, also show the image (filename is data/images/<tourId>.jpg)
        } catch (BadRequestException e) {
            logger.warn("BusinessLogic.createTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.createTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.createTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToParseImageFileException e) {
            logger.error("BusinessLogic.createTour() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void getTourList() {
        try {
            List<TourListEntry> tourList = tourService.getTourList();
            logger.info("BusinessLogic.getTourList() - tourList retrieved successfully: " + tourList);
            // TODO: show the list in ui
        } catch (NoContentException e) {
            logger.info("BusinessLogic.getTourList() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.getTourList() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.getTourList() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void searchTour(String keyword) {
        try {
            List<TourListEntry> tourList = tourService.searchTour(keyword);
            logger.info("BusinessLogic.searchTour() - tourList retrieved successfully: " + tourList);
            // TODO: show the list in ui
        } catch (NoContentException e) {
                logger.info("BusinessLogic.searchTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (NotFoundException e) {
            logger.info("BusinessLogic.searchTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.searchTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.searchTour() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void getTour(Integer tourId) {
        try {
            Tour tour = tourService.getTour(tourId);
            ImageHandler.saveBase64EncodedImageToFile(tour.getTourInformation(), tour.getTourId().toString());
            logger.info("BusinessLogic.getTour() - tour retrieved successfully: " + tour);
            // TODO: show the tour in ui, also show the image (filename is data/images/<tourId>.jpg)
        } catch (NotFoundException e) {
            logger.info("BusinessLogic.getTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.getTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToParseImageFileException e) {
            logger.error("BusinessLogic.getTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.getTour() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void updateTour(Integer tourId, Tour tour) {
        try {
            tour = tourService.updateTour(tourId, tour);
            ImageHandler.saveBase64EncodedImageToFile(tour.getTourInformation(), tour.getTourId().toString());
            logger.info("BusinessLogic.updateTour() - tour updated successfully: " + tour);
            // TODO: show the updated tour in ui, also show the updated image (filename is data/images/<tourId>.jpg)
        } catch (NotFoundException e) {
            logger.info("BusinessLogic.updateTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (BadRequestException e) {
            logger.warn("BusinessLogic.updateTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.updateTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToParseImageFileException e) {
            logger.error("BusinessLogic.updateTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.updateTour() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void deleteTour(Integer tourId) {
        try {
            tourService.deleteTour(tourId);
            logger.info("BusinessLogic.deleteTour() - tour deleted successfully: " + tourId);
            // TODO: remove the tour from ui
        } catch (NotFoundException e) {
            logger.info("BusinessLogic.deleteTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.deleteTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.deleteTour() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void createTourLog(Integer tourId, TourLog tourLog) {
        try {
            tourLog = tourLogService.createTourLog(tourId, tourLog);
            logger.info("BusinessLogic.createTourLog() - tourLog created successfully: " + tourLog);
            // TODO: show created tourLog in ui
        } catch (NotFoundException e) {
            logger.info("BusinessLogic.deleteTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.deleteTour() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.deleteTour() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void getTourLogs(Integer tourId) {
        try {
            List<TourLog> tourLogs= tourLogService.getTourLogs(tourId);
            logger.info("BusinessLogic.getTourLogs() - tourLogs retrieved successfully: " + tourLogs);
            // TODO: show tourLogs in ui
        } catch (NoContentException e) {
            logger.info("BusinessLogic.getTourLogs() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (NotFoundException e) {
            logger.info("BusinessLogic.getTourLogs() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.getTourLogs() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.getTourLogs() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void getTourLog(Integer tourLogId) {
        try {
            TourLog tourLog= tourLogService.getTourLog(tourLogId);
            logger.info("BusinessLogic.getTourLog() - tourLog retrieved successfully: " + tourLog);
            // TODO: show tourLog in ui
        } catch (NotFoundException e) {
            logger.info("BusinessLogic.getTourLog() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.getTourLog() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.getTourLog() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void updateTourLog(Integer tourLogId, TourLog tourLog) {
        try {
            tourLog = tourLogService.updateTourLog(tourLogId, tourLog);
            logger.info("BusinessLogic.updateTourLog() - tourLog updated successfully: " + tourLog);
            // TODO: show updated tourLog in ui
        } catch (NotFoundException e) {
            logger.info("BusinessLogic.updateTourLog() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.updateTourLog() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.updateTourLog() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void deleteTourLog(Integer tourLogId) {
        try {
            tourLogService.deleteTourLog(tourLogId);
            logger.info("BusinessLogic.deleteTourLog() - tourLog deleted successfully: " + tourLogId);
            // TODO: remove the tourLog from ui
        } catch (NotFoundException e) {
            logger.info("BusinessLogic.deleteTourLog() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.deleteTourLog() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.deleteTourLog() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void exportTours(String filename) {
        try {
            List<Tour> tours = dataTransferService.exportTours(filename);
            logger.info("BusinessLogic.exportTours() - tours exported successfully: " + filename);
            // TODO: show success popup in ui
        } catch (NoContentException e) {
            logger.info("BusinessLogic.exportTours() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.exportTours() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToParseJsonFileException e) {
            logger.error("BusinessLogic.exportTours() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.exportTours() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void importTours(String filename) {
        try {
            List<Tour> tours = dataTransferService.importTours(filename);
            logger.info("BusinessLogic.importTours() - tours imported successfully: " + tours);
            // TODO: show success popup in ui, show imported tours in ui
        } catch (BadRequestException e) {
            logger.warn("BusinessLogic.importTours() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.importTours() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToParseJsonFileException e) {
            logger.error("BusinessLogic.importTours() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.importTours() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void createSummarizeReport(String filename) {
        try {
            PdfFileHandler pdfFileHandler = new PdfFileHandler();
            List<Tour> tours = dataTransferService.exportTours(null);
            for(Tour tour : tours) {
                ImageHandler.saveBase64EncodedImageToFile(tour.getTourInformation(), tour.getTourId().toString());
            }
            pdfFileHandler.createSummarizeReport(tours, filename);
            logger.info("BusinessLogic.createSummarizeReport() - report created successfully: " + filename);
            // TODO: show success popup in ui
        } catch (NoContentException e) {
            logger.info("BusinessLogic.createSummarizeReport() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.createSummarizeReport() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToParseJsonFileException e) {
            logger.error("BusinessLogic.createSummarizeReport() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToCreatePdfFileException e) {
            logger.error("BusinessLogic.createSummarizeReport() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.createSummarizeReport() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
    public void createTourReport(Integer tourId, String filename) {
        try {
            PdfFileHandler pdfFileHandler = new PdfFileHandler();
            Tour tour = tourService.getTour(tourId);
            ImageHandler.saveBase64EncodedImageToFile(tour.getTourInformation(), tourId.toString());
            pdfFileHandler.createTourReport(tour, filename);
            logger.info("BusinessLogic.createTourReport() - report created successfully: " + filename);
        } catch (NoContentException e) {
            logger.info("BusinessLogic.createTourReport() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (NotFoundException e) {
            logger.info("BusinessLogic.createTourReport() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (InternalServerErrorException e) {
            logger.error("BusinessLogic.createTourReport() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToParseJsonFileException e) {
            logger.error("BusinessLogic.createTourReport() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToCreatePdfFileException e) {
            logger.error("BusinessLogic.createTourReport() - " + e.getMessage());
            // TODO: handle exception properly
        } catch (FailedToSendRequestException e) {
            logger.error("BusinessLogic.createTourReport() - " + e.getMessage());
            // TODO: handle exception properly
        }
    }
}
