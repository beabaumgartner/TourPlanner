package at.fhtw.viewmodel;

import at.fhtw.exceptions.*;
import at.fhtw.models.Tour;
import at.fhtw.models.TourListEntry;
import at.fhtw.services.TourService;
import at.fhtw.utils.ImageHandler;
import at.fhtw.view.ShowTourInformationView;
import at.fhtw.view.ShowTourLogsView;
import at.fhtw.view.popUps.DialogView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ListToursViewModel {
    private static final Logger logger = LogManager.getLogger(ListToursViewModel.class);
    private static ListToursViewModel instance;
    private final TourService tourService;
    private List<TourListEntry> masterData;
    private ObservableList<TourListEntry> tourListItems;

    public ListToursViewModel()
    {
        this.tourService = new TourService();
        masterData = new ArrayList<>();
        tourListItems = FXCollections.observableArrayList();
    }

    public static ListToursViewModel getInstance()
    {
        if(instance == null)
        {
            instance = new ListToursViewModel();
        }
        return instance;
    }

    public List<TourListEntry> getMasterDataListItems() {
        return masterData;
    }
    public ObservableList<TourListEntry> getTourListItems() {
        return tourListItems;
    }

    public void addItem(TourListEntry tour) {
        tourListItems.add(tour);
        masterData.add(tour);
    }
    public void clearItems(){ tourListItems.clear(); }

    public void initList(){
        try {
            tourService.getTourList().forEach(p -> {
                addItem(p);
            });
            tourListItems.sort(Comparator.comparingInt(TourListEntry::getTourId));
            logger.info("ListToursViewModel.getTourList() - tourList retrieved successfully: " + tourListItems);
        } catch (NoContentException e) {
            logger.info("ListToursViewModel.getTourList() - " + e.getMessage());
        } catch (InternalServerErrorException e) {
            logger.error("ListToursViewModel.getTourList() - " + e.getMessage());
            new DialogView("Internal Server Issues\nTour could not be loaded!", "Get Tourlist");
        } catch (FailedToSendRequestException e) {
            logger.error("ListToursViewModel.getTourList() - " + e.getMessage());
            new DialogView("Failed to send Request", "Get Tourlist");
        }
    }

    public Tour getTour(Integer tourId){
        try {
            Tour tour = tourService.getTour(tourId);
            ImageHandler.saveBase64EncodedImageToFile(tour.getTourInformation(), tour.getTourId().toString());
            logger.info("ListToursViewModel.getTour() - tour retrieved successfully: " + tour);
            return tour;
        } catch (NotFoundException e) {
            logger.info("ListToursViewModel.getTour() - " + e.getMessage());
            new DialogView("Tour not found", "Get Tour");
        } catch (InternalServerErrorException e) {
            logger.error("ListToursViewModel.getTour() - " + e.getMessage());
            new DialogView("Internal Server Issues", "Get Tour");
        } catch (FailedToParseImageFileException e) {
            logger.error("ListToursViewModel.getTour() - " + e.getMessage());
            new DialogView("Failed to parse imagefile", "Get Tour");
        } catch (FailedToSendRequestException e) {
            logger.error("ListToursViewModel.getTour() - " + e.getMessage());
            new DialogView("Failed to send Request", "Get Tour");
        }
        return null;
    }

    public void filterList(String keyword){
        try {
            tourService.searchTour(keyword).forEach(p -> {
                addItem(p);
            });
            logger.info("ListToursViewModel.searchTour() - tourList retrieved successfully: " + tourListItems);
        } catch (NoContentException e) {
            logger.info("ListToursViewModel.searchTour() - " + e.getMessage());
            new DialogView("No Content Found!", "Search Tour");
        } catch (NotFoundException e) {
            logger.info("ListToursViewModel.searchTour() - " + e.getMessage());
            new DialogView("No Tour Found!", "Search Tour");
        } catch (InternalServerErrorException e) {
            logger.error("ListToursViewModel.searchTour() - " + e.getMessage());
            new DialogView("Internal Server Issues\nTour could not be searched!", "Search Tour");
        } catch (FailedToSendRequestException e) {
            logger.error("ListToursViewModel.searchTour() - " + e.getMessage());
            new DialogView("Failed to send Request\nTour could not be searched!", "Search Tour");
        }
    }

    public void addTour(Tour createdTour) {
        try {
            Tour tour = new TourService().createTour(createdTour);
            ShowTourInformationView.getInstance().hideInformation();
            ShowTourLogsView.getInstance().hideTourLogs();
            ImageHandler.saveBase64EncodedImageToFile(tour.getTourInformation(), tour.getTourId().toString());
            logger.info("ListToursViewModel.createTour() - tour created successfully: " + tour);
            new DialogView("Tour added successfully", "Add Tour");
        } catch (BadRequestException e) {
            logger.warn("ListToursViewModel.createTour() - " + e.getMessage());
            new DialogView("Bad Request\nTour could not be created!", "Create Tour");
        } catch (InternalServerErrorException e) {
            logger.error("ListToursViewModel.createTour() - " + e.getMessage());
            new DialogView("Internal Server Issues\nTour could not be created!", "Create Tour");
        } catch (FailedToSendRequestException e) {
            logger.error("ListToursViewModel.createTour() - " + e.getMessage());
            new DialogView("Failed to send Request\nTour could not be created!", "Create Tour");
        } catch (FailedToParseImageFileException e) {
            logger.error("ListToursViewModel.createTour() - " + e.getMessage());
            new DialogView("Failed to parse image\nTour could not be created!", "Create Tour");
        }
    }
    public void updateTour(Tour updatedTour, Integer tourId) {
        try {
            Tour tour = new TourService().updateTour(tourId, updatedTour);
            ShowTourInformationView.getInstance().hideInformation();
            ShowTourLogsView.getInstance().hideTourLogs();
            ImageHandler.saveBase64EncodedImageToFile(tour.getTourInformation(), tour.getTourId().toString());
            logger.info("ListToursViewModel.updateTour() - tour updated successfully: " + tour);
            new DialogView("Tour updated successfully", "Update Tour");
        } catch (NotFoundException e) {
            logger.info("ListToursViewModel.updateTour() - " + e.getMessage());
            new DialogView("Tour could not be found", "Update Tour");
        } catch (BadRequestException e) {
            logger.warn("ListToursViewModel.updateTour() - " + e.getMessage());
            new DialogView("Bad Request\nTour could not be updated!", "Update Tour");
        } catch (InternalServerErrorException e) {
            logger.error("ListToursViewModel.updateTour() - " + e.getMessage());
            new DialogView("Internal Server Issues\nThe Tour Information could not be shown!", "Update Tour");
        } catch (FailedToParseImageFileException e) {
            logger.error("ListToursViewModel.updateTour() - " + e.getMessage());
            new DialogView("Failed to parse image\nThe Tour Information could not be shown!", "Update Tour");
        } catch (FailedToSendRequestException e) {
            logger.error("ListToursViewModel.updateTour() - " + e.getMessage());
            new DialogView("Failed to send Request\nThe Tour Information could not be shown!", "Update Tour");
        }
    }

    public void deleteTour(Integer tourId)
    {
        try {
            tourService.deleteTour(tourId);
            ShowTourInformationView.getInstance().hideInformation();
            ShowTourLogsView.getInstance().hideTourLogs();
            logger.info("ListToursViewModel.deleteTour() - tour deleted successfully: " + tourId);
            new DialogView("Tour deleted successfully", "Delete Tour");
        } catch (NotFoundException e) {
            logger.info("ListToursViewModel.deleteTour() - " + e.getMessage());
            new DialogView("Tour Not Found\nTour could not be deleted!", "Delete Tour");
        } catch (InternalServerErrorException e) {
            logger.error("ListToursViewModel.deleteTour() - " + e.getMessage());
            new DialogView("Internal Server Issues\nTour could not be deleted!", "Delete Tour");
        } catch (FailedToSendRequestException e) {
            logger.error("ListToursViewModel.deleteTour() - " + e.getMessage());
            new DialogView("Failed to send Request\nTour could not be deleted!", "Delete Tour");
        }
    }
}
