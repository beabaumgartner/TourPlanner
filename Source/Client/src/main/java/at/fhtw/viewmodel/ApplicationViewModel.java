package at.fhtw.viewmodel;

import at.fhtw.exceptions.*;
import at.fhtw.models.Tour;
import at.fhtw.services.DataTransferService;
import at.fhtw.utils.ImageHandler;
import at.fhtw.utils.pdfFileHandler.PdfFileHandler;
import at.fhtw.view.popUps.DialogView;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationViewModel {
    private static final Logger logger = LogManager.getLogger(ApplicationViewModel.class);
    private DataTransferService dataTransferService;
    public ApplicationViewModel()
    {
        dataTransferService = new DataTransferService();
    }
    public void exportTours(String filename) {
        try {
            List<Tour> tours = dataTransferService.exportTours(filename);
            logger.info("ApplicationViewModel.exportTours() - tours exported successfully: " + filename);
            new DialogView("Tours exported successfully!", "Export Tours");
        } catch (NoContentException e) {
            logger.info("ApplicationViewModel.exportTours() - " + e.getMessage());
            new DialogView("No content found", "Export Tours");
        } catch (InternalServerErrorException e) {
            logger.error("ApplicationViewModel.exportTours() - " + e.getMessage());
            new DialogView("Internal Server Issues\nTour export failed!", "Export Tours");
        } catch (FailedToParseJsonFileException e) {
            logger.error("ApplicationViewModel.exportTours() - " + e.getMessage());
            new DialogView("Failed to parse jsonfile\nTour export failed!", "Export Tours");
        } catch (FailedToSendRequestException e) {
            logger.error("ApplicationViewModel.exportTours() - " + e.getMessage());
            new DialogView("Failed to send Request\nTour export failed!", "Export Tours");
        }
    }

    public void importTours(String filename) {
        try {
            List<Tour> tours = dataTransferService.importTours(filename);
            logger.info("ApplicationViewModel.importTours() - tours imported successfully: " + tours);
            new DialogView("Tours imported successfully!", "Import Tours");
        } catch (BadRequestException e) {
            logger.warn("ApplicationViewModel.importTours() - " + e.getMessage());
            new DialogView("Bad Request\nTour import failed!", "Import Tours");
        } catch (InternalServerErrorException e) {
            logger.error("ApplicationViewModel.importTours() - " + e.getMessage());
            new DialogView("Internal Server Issues\nTour import failed!", "Import Tours");
        } catch (FailedToParseJsonFileException e) {
            logger.error("ApplicationViewModel.importTours() - " + e.getMessage());
            new DialogView("Failed to parse jsonfile\nTour import failed!", "Import Tours");
        } catch (FailedToSendRequestException e) {
            logger.error("ApplicationViewModel.importTours() - " + e.getMessage());
            new DialogView("Failed to send Request\nTour import failed!", "Import Tours");
        }
    }
    public void createSummarizeReport(String filename) {
        try {
            PdfFileHandler pdfFileHandler = new PdfFileHandler();
            List<Tour> tours = dataTransferService.exportTours(null);
            for (Tour tour : tours) {
                ImageHandler.saveBase64EncodedImageToFile(tour.getTourInformation(), tour.getTourId().toString());
            }
            pdfFileHandler.createSummarizeReport(tours, filename);
            logger.info("ApplicationViewModel.createSummarizeReport() - report created successfully: " + filename);
            new DialogView("Summarize Report created successfully!", "Summarize Report");
        } catch (NoContentException e) {
            logger.info("ApplicationViewModel.createSummarizeReport() - " + e.getMessage());
            new DialogView("No content found!", "Create Summarize Report");
        } catch (InternalServerErrorException e) {
            logger.error("ApplicationViewModel.createSummarizeReport() - " + e.getMessage());
            new DialogView("Internal Server Issues!\nFailed to create Summarize Report!", "Create Summarize Report");
        } catch (FailedToParseJsonFileException e) {
            logger.error("ApplicationViewModel.createSummarizeReport() - " + e.getMessage());
            new DialogView("Failed to parse jsonfile!\nFailed to create Summarize Report!", "Create Summarize Report");
        } catch (FailedToCreatePdfFileException e) {
            logger.error("ApplicationViewModel.createSummarizeReport() - " + e.getMessage());
            new DialogView("Failed to parse image!\nFailed to create Summarize Report!", "Create Summarize Report");
        } catch (FailedToSendRequestException e) {
            logger.error("ApplicationViewModel.createSummarizeReport() - " + e.getMessage());
            new DialogView("Failed to send Request!\nFailed to create Summarize Report!", "Create Summarize Report");
        }
    }
}
