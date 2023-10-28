package at.fhtw.view;

import at.fhtw.models.TourLog;
import at.fhtw.view.popUps.CreateTourLogPopUpView;
import at.fhtw.view.popUps.DeleteTourLogMessageView;
import at.fhtw.view.popUps.UpdateTourLogPopUpView;
import at.fhtw.viewmodel.ShowTourLogsViewModel;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowTourLogsView extends ApplicationView{
    private static ShowTourLogsViewModel showTourLogsViewModel;
    @FXML
    public AnchorPane tourLogPane;
    public VBox paneContainer;
    @FXML
    private VBox dataContainer;


    public ShowTourLogsView()
    {
        this.showTourLogsViewModel = new ShowTourLogsViewModel();
    }

    public static ShowTourLogsViewModel getInstance()
    {
        if(showTourLogsViewModel == null)
        {
            showTourLogsViewModel = new ShowTourLogsViewModel();
        }
        return showTourLogsViewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {

        createInformationTextField("Please select a Tour to see Information!");

        showTourLogsViewModel.hideTourInformationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                paneContainer.getChildren().clear();
                createInformationTextField("Please select a Tour to see Information!");
                showTourLogsViewModel.setHideTourInformation(false);
            }
        });

        showTourLogsViewModel.showTourInformationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                paneContainer.getChildren().clear();
                paneContainer.setAlignment(Pos.BASELINE_LEFT);
                showTourLogs();
                showTourLogsViewModel.setShowTourInformation(false);
            }
        });

        showTourLogsViewModel.isNoTourLogContentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                paneContainer.getChildren().clear();
                createInformationTextFieldWithButton("This Tour has no Tour Logs");
                showTourLogsViewModel.setIsNoTourLogContent(false);
            }
        });
    }

    private void createInformationTextField(String text)
    {
        paneContainer.setAlignment(Pos.CENTER);
        Label informationTextField = new Label();
        informationTextField.setText(text);
        informationTextField.setAlignment(Pos.CENTER);
        informationTextField.setStyle("-fx-font-size: 14px;");
        paneContainer.getChildren().add(informationTextField);
    }

    private void createInformationTextFieldWithButton(String text)
    {
        HBox hbox = new HBox();
        AnchorPane.setRightAnchor(hbox, 0.0);
        AnchorPane.setLeftAnchor(hbox, 0.0);
        AnchorPane.setTopAnchor(hbox, 0.0);
        AnchorPane.setBottomAnchor(hbox, 0.0);
        VBox informstionTextBox = new VBox();
        informstionTextBox.setSpacing(10);
        informstionTextBox.setAlignment(Pos.CENTER);

        hbox.setAlignment(Pos.CENTER);
        Label informationTextField = new Label();
        informationTextField.setText(text);
        informationTextField.setAlignment(Pos.CENTER);
        informationTextField.setStyle("-fx-font-size: 14px;");

        Button createTourLogButton = new Button("Create Tour Log");
        createTourLogButton.setAlignment(Pos.CENTER);
        createTourLogButton.getStyleClass().add("important-button");

        informstionTextBox.getChildren().add(informationTextField);
        informstionTextBox.getChildren().add(createTourLogButton);
        hbox.getChildren().addAll(informstionTextBox);
        paneContainer.getChildren().add(hbox);

        createTourLogButton.setOnAction(e -> onCreateTourLog(showTourLogsViewModel.getTourListEntry().getTourId()));
    }

    public void showTourLogs()
    {
        dataContainer = new VBox();
        dataContainer.setSpacing(10);
        dataContainer.setPadding(new Insets(15, 15, 15, 15));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 0, 10, 0));
        hbox.setSpacing(10.0);
        dataContainer.getChildren().add(hbox);

        VBox tourNameBox = new VBox();
        VBox headerLabelBox = new VBox();
        VBox createButtonBox = new VBox();

        hbox.getChildren().addAll(tourNameBox, headerLabelBox, createButtonBox);
        dataContainer.getChildren().add(new Separator());

        HBox.setHgrow(createButtonBox, javafx.scene.layout.Priority.ALWAYS);

        Label tourName = new Label("Tour Name: ");
        Label tourLogHeader = new Label(showTourLogsViewModel.getTourListEntry().getName());

        tourName.getStyleClass().add("headers");
        tourLogHeader.getStyleClass().add("headers");

        tourNameBox.getChildren().add(tourName);
        headerLabelBox.getChildren().add(tourLogHeader);

        Button createTourLogButton = new Button("Create Tour Log");
        createTourLogButton.setMinWidth(80);
        createButtonBox.setAlignment(Pos.CENTER_RIGHT);
        createTourLogButton.getStyleClass().add("important-button");

        createButtonBox.getChildren().add(createTourLogButton);

        javafx.scene.control.ScrollPane scrollPane = new ScrollPane(dataContainer);
        scrollPane.setFitToWidth(true);

        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(dataContainer, 0.0);
        AnchorPane.setLeftAnchor(dataContainer, 0.0);
        AnchorPane.setTopAnchor(dataContainer, 0.0);
        AnchorPane.setBottomAnchor(dataContainer, 0.0);

        paneContainer.getChildren().add(scrollPane);
        scrollPane.setPrefSize(400, 600);

        createTourLogButton.setOnAction(e -> onCreateTourLog(showTourLogsViewModel.getTourListEntry().getTourId()));

        showTourLogsViewModel.getTourLogs().forEach(p -> {
            createTourLogsView(p);
        });
    }

    public void createTourLogsView(TourLog tourLog)
    {
        HBox hbox = new HBox();
        VBox headers = new VBox();
        VBox content = new VBox();
        VBox buttons = new VBox();

        HBox.setHgrow(buttons, javafx.scene.layout.Priority.ALWAYS);

        headers.setSpacing(5);
        content.setSpacing(5);
        buttons.setSpacing(0);
        dataContainer.setSpacing(10.0);

        dataContainer.getChildren().add(hbox);
        hbox.getChildren().addAll(headers, content, buttons);

        Label date = new Label("Date: ");
        date.getStyleClass().add("bold-headers");
        Label totalTime = new Label("Total Time: ");
        totalTime.getStyleClass().add("bold-headers");
        Label difficulty = new Label("Difficulty: ");
        difficulty.getStyleClass().add("bold-headers");
        Label rating = new Label("Rating: ");
        rating.getStyleClass().add("bold-headers");
        Label comment = new Label("Comment: ");
        comment.getStyleClass().add("bold-headers");

        headers.getChildren().addAll(date, totalTime, difficulty, rating, comment);

        Label dateTourLog = new Label(tourLog.getDate());
        Label totalTimeTourLog = new Label(showTourLogsViewModel.calculateTotalTime(tourLog.getTotalTime()));
        Label difficultyTourLog = new Label(showTourLogsViewModel.calculateStarRatings(tourLog.getDifficulty()));
        Label ratingTourLog = new Label(showTourLogsViewModel.calculateStarRatings(tourLog.getRating()));
        Label commentTourLog = new Label(tourLog.getComment());
        commentTourLog.setWrapText(true);

        dataContainer.getChildren().add(new Separator());

        content.getChildren().addAll(dateTourLog, totalTimeTourLog,
                                    difficultyTourLog, ratingTourLog,
                                    commentTourLog);

        Button updateButton = new Button("Update");
        updateButton.setMinWidth(80);
        Button deleteButton = new Button("Delete");
        deleteButton.setMinWidth(80);
        buttons.getChildren().addAll(updateButton, deleteButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        updateButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                onUpdateTourLog(tourLog);
            }
        });

        deleteButton.setOnAction(e -> onDeleteTourLog(tourLog));
    }

    public void onCreateTourLog(Integer tourId)
    {
        new CreateTourLogPopUpView(tourId, "Create Tour");
    }

    public void onDeleteTourLog(TourLog tourLog)
    {
        new DeleteTourLogMessageView(tourLog, showTourLogsViewModel.getTourListEntry().getTourId(), "Delete Tour Log");
    }
    public void onUpdateTourLog(TourLog tourLog)
    {
        new UpdateTourLogPopUpView(tourLog, showTourLogsViewModel.getTourListEntry().getTourId(), "Update Tour");
    }
}
