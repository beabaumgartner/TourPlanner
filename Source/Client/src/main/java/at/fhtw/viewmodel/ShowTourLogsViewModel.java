package at.fhtw.viewmodel;

import at.fhtw.exceptions.*;
import at.fhtw.models.TourListEntry;
import at.fhtw.models.TourLog;
import at.fhtw.services.TourLogService;
import at.fhtw.view.popUps.DialogView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShowTourLogsViewModel {
    private static final Logger logger = LogManager.getLogger(ShowTourLogsViewModel.class);
    TourLogService tourLogService;
    BooleanProperty hideTourInformation = new SimpleBooleanProperty();
    BooleanProperty showTourInformation  = new SimpleBooleanProperty();
    BooleanProperty isNoTourLogContent = new SimpleBooleanProperty();
    TourListEntry tourListEntry;
    List<TourLog> tourLogs;

    public ShowTourLogsViewModel()
    {
        hideTourInformation.setValue(false);
        showTourInformation.setValue(false);
        tourLogService = new TourLogService();
        tourLogs = new ArrayList<>();
    }

    public void hideTourLogs()
    {
        hideTourInformation.setValue(true);
    }
    public void clearItems(){ tourLogs.clear(); }

    public void showTourLogs(Integer tourId)
    {
        try {
            hideTourInformation.setValue(true);
            tourLogs = tourLogService.getTourLogs(tourId);

            showTourInformation.setValue(true);
            logger.info("ShowTourLogsViewModel.getTourLogs() - tourLogs retrieved successfully: " + tourLogs);
        } catch (NoContentException e) {
            logger.info("ListToursView.getTourLogs() - " + e.getMessage());
            isNoTourLogContent.set(true);
        } catch (NotFoundException e) {
            logger.info("ShowTourLogsViewModel.getTourLogs() - " + e.getMessage());
            new DialogView("Tour Log could not be found", "Tour Log");
        } catch (InternalServerErrorException e) {
            logger.error("ShowTourLogsViewModel.getTourLogs() - " + e.getMessage());
            new DialogView("Internal Server Issues\nThe Tour Log could not be shown!", "Tour Log");
        } catch (FailedToSendRequestException e) {
            logger.error("ShowTourLogsViewModel.getTourLogs() - " + e.getMessage());
            new DialogView("Failed to send Request\nThe Tour Log could not be shown!", "Tour Log");
        }
    }

    public void createTourLog(Integer tourId, TourLog createdTourLog) {
        try {
            TourLog tourLog = tourLogService.createTourLog(tourId, createdTourLog);
            logger.info("ShowTourLogsViewModel.createTourLog() - tourLog created successfully: " + tourLog);
            showTourLogs(tourId);
            new DialogView("Tour Log successfully created", "Create Tour Log");
        } catch (NoContentException e) {
            logger.info("ShowTourLogsViewModel.getTourLogs() - " + e.getMessage());
        } catch (NotFoundException e) {
            logger.info("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Tour could not be found", "Create Tour");
        } catch (InternalServerErrorException e) {
            logger.error("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Internal Server Issues\nThe Tour Information could not be created!", "Create Tour");
        } catch (FailedToParseImageFileException e) {
            logger.error("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Failed to parse image\nnThe Tour Tour Log could be not shown!", "Create Tour");
        }catch (FailedToSendRequestException e) {
            logger.error("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Failed to send Request\nThe Tour Information could not be created!", "Create Tour");
        }
    }

    public void updateTourLogs(TourLog updatedTourLog, Integer tourLogId)
    {
        try {
            TourLog tourLog = tourLogService.updateTourLog(tourLogId, updatedTourLog);
            logger.info("ShowTourLogsViewModel.updateTourLog() - tourLog updated successfully: " + tourLog);
            new DialogView("Tour Log successfully updated", "Update Tour Log");
        } catch (NoContentException e) {
            logger.info("ShowTourLogsViewModel.getTourLogs() - " + e.getMessage());
        } catch (NotFoundException e) {
            logger.info("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Tour could not be found", "Update Tour");
        } catch (InternalServerErrorException e) {
            logger.error("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Internal Server Issues\nThe Tour Information could not be shown!", "Update Tour");
        } catch (FailedToParseImageFileException e) {
            logger.error("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Failed to parse image\nThe Tour Tour Log could not be shown!", "Update Tour");
        } catch (FailedToSendRequestException e) {
            logger.error("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Failed to send Request\nThe Tour Information could not be shown!", "Update Tour");
        }
    }

    public void deleteTourLogs(Integer tourLogId)
    {
        try {
            tourLogService.deleteTourLog(tourLogId);
            logger.info("ShowTourLogsViewModel.deleteTourLog() - tourLog deleted successfully: " + tourLogId);
            new DialogView("Tour Log successfully deleted", "Delete Tour Log");
        } catch (NoContentException e) {
            logger.info("ShowTourLogsViewModel.getTourLogs() - " + e.getMessage());
        } catch (NotFoundException e) {
            logger.info("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Tour could not be found", "Delete Tour Log");
        } catch (InternalServerErrorException e) {
            logger.error("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Internal Server Issues\nThe Tour Tour Log could not be deleted!", "Delete Tour Log");
        } catch (FailedToParseImageFileException e) {
            logger.error("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Failed to parse image\nThe Tour Tour Log could be not shown!", "Delete Tour Log");
        } catch (FailedToSendRequestException e) {
            logger.error("ShowTourLogsViewModel.getTour() - " + e.getMessage());
            new DialogView("Failed to send Request\nThe Tour Tour Log could not be deleted!", "Delete Tour Log");
        }
    }

    public String calculateTotalTime(Integer estimatedTime)
    {
        int minutes = estimatedTime / 60;
        int hours = minutes / 60;
        int days = hours / 24;

        String estimatedTimeString= "";

        if(days >= 1)
        {
            int remainingHours = hours % 24;
            int remainingMinutes = minutes % 60;
            estimatedTimeString = days + "d " + remainingHours + "h " + remainingMinutes + "min";
        }
        else if(hours >= 1)
        {
            int remainingMinutes = minutes % 60;
            estimatedTimeString = hours + "h " + remainingMinutes + "min";
        }
        else if(minutes >= 1)
        {
            estimatedTimeString = minutes + "min";
        }
        else
        {
            estimatedTimeString = "0." + estimatedTime + "min";
        }
        return estimatedTimeString;
    }

    public String calculateStarRatings(Integer rating)
    {
        Integer totalAmountOfStars = 5;
        String starString= "";

        for(int i = 1 ; i <= totalAmountOfStars; i++)
        {
            if(i > rating)
            {
                starString += "☆";
            }
            else
            {
                starString += "★";
            }
        }
        return starString;
    }

    public Integer calculateTotalTimeFromInput(Integer days, Integer hours, Integer minutes)
    {
        Integer totalSeconds = 0;

        totalSeconds += days * 24 * 60 * 60;
        totalSeconds += hours * 60 * 60;
        totalSeconds += minutes * 60;

        return totalSeconds;
    }

    public void calculateTime(Integer totalTime, TextField daysTextField, TextField hoursTextField, TextField minutesTextField) {
        int minutes = totalTime / 60;
        int hours = minutes / 60;
        int days = hours / 24;

        int remainingMinutes = minutes % 60;
        int remainingHours = hours % 24;

        daysTextField.setText(Integer.toString(days));
        hoursTextField.setText(Integer.toString(remainingHours));
        minutesTextField.setText(Integer.toString(remainingMinutes));
    }

    public boolean isHideTourInformation() {
        return hideTourInformation.get();
    }

    public BooleanProperty hideTourInformationProperty() {
        return hideTourInformation;
    }

    public void setHideTourInformation(boolean hideTourInformation) {
        this.hideTourInformation.set(hideTourInformation);
    }

    public boolean isShowTourInformation() {
        return showTourInformation.get();
    }

    public BooleanProperty showTourInformationProperty() {
        return showTourInformation;
    }

    public void setShowTourInformation(boolean showTourInformation) {
        this.showTourInformation.set(showTourInformation);
    }

    public boolean getIsNoTourLogContent() {
        return isNoTourLogContent.get();
    }

    public BooleanProperty isNoTourLogContentProperty() {
        return isNoTourLogContent;
    }

    public void setIsNoTourLogContent(boolean isNoTourLogContent) {
        this.isNoTourLogContent.set(isNoTourLogContent);
    }

    public TourListEntry getTourListEntry() {
        return tourListEntry;
    }

    public List<TourLog> getTourLogs() {
        return tourLogs;
    }

    public void setTourListEntry(TourListEntry tourListEntry) {
        this.tourListEntry = tourListEntry;
    }
}
