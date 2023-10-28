package at.fhtw.view.popUps;

import at.fhtw.models.TourLog;
import at.fhtw.view.ShowTourLogsView;
import at.fhtw.viewmodel.ShowTourLogsViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteTourLogMessageView extends Dialog<Void> {
    private static final Logger logger = LogManager.getLogger(DeleteTourLogMessageView.class);
    TourLog tourLog;
    Integer tourId;
    String message;
    String title;
    public DeleteTourLogMessageView(TourLog tourLog, Integer tourId, String title) {
        super();
        this.tourLog = tourLog;
        this.tourId = tourId;
        this.message = "Are you sure you want to delete the Tour Log with id " + tourLog.getTourLogId();
        this.title = title;

        initialize();
    }

    public void initialize() {
        Stage popupWindow = new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);

        Label label = new Label(message);

        Button submitButton= new Button("Submit");
        Button cancelButton= new Button("Cancel");

        submitButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                onDeleteTourLog();
                popupWindow.close();
            }
        });

        cancelButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                popupWindow.close();
            }
        });
        HBox buttonBox = new HBox(10, submitButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(16, 16, 16, 16));

        VBox layout = new VBox();
        layout.setPadding(new Insets(16, 16, 16, 16));
        layout.setMaxWidth(300);

        layout.getChildren().addAll(label, buttonBox);

        Scene scene = new Scene(layout);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    public void onDeleteTourLog()
    {
        ShowTourLogsViewModel showTourLogsViewModel = ShowTourLogsView.getInstance();
        showTourLogsViewModel.deleteTourLogs(tourLog.getTourLogId());
        showTourLogsViewModel.showTourLogs(tourId);
    }
}
