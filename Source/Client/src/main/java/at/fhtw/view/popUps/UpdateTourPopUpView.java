package at.fhtw.view.popUps;

import at.fhtw.models.Tour;
import at.fhtw.view.ListToursView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateTourPopUpView extends Dialog<Void> {
    Tour tour;
    String title;
    @FXML
    private Stage popupWindow = new Stage();
    @FXML
    private Label feedbackText = new Label();
    @FXML
    public TextField tourNameTextField = new TextField();
    @FXML
    public TextField descriptionTextField = new TextField();
    @FXML
    private TextField startTextField = new TextField();
    @FXML
    private TextField destinationTextField = new TextField();
    @FXML
    private ChoiceBox<String> transportTypeChoiceBox = new ChoiceBox<>();
    private String defaultStylesheet = "file:src/main/resources/at/fhtw/css_sheets/application.css";
    public UpdateTourPopUpView(Tour tour, String title) {
        super();
        this.tour = tour;
        this.title = title;

        initialize();
    }

    public void initialize() {

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);
        feedbackText.getStyleClass().add("feedbackText");

        VBox root = new VBox();
        root.setPadding(new Insets(8.0));
        root.getStylesheets().add(defaultStylesheet);


        Label titleLabel = new Label("Update Tour");
        titleLabel.setFont(new Font(14.0));

        Separator separator1 = new Separator();
        separator1.setPrefWidth(200.0);

        Text tourNameText = new Text("Tour Name");
        tourNameTextField.setText(tour.getName());

        Text descriptionText = new Text("Description");
        descriptionTextField.setText(tour.getTourDescription());

        Text startText = new Text("Start");
        startTextField.setText(tour.getStart());

        Text destinationText = new Text("Destination");
        destinationTextField.setText(tour.getDestination());

        Text transportTypeText = new Text("Transport Type");
        transportTypeChoiceBox.getItems().addAll("car", "bicycle", "pedestrian");
        transportTypeChoiceBox.setPrefHeight(30);
        transportTypeChoiceBox.setValue(tour.getTransportType());

        HBox submitButtonContainer = new HBox();
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> onSubmit());
        submitButtonContainer.getChildren().add(submitButton);
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                popupWindow.close();
            }
        });
        submitButtonContainer.getChildren().add(cancelButton);
        submitButtonContainer.setAlignment(Pos.BASELINE_RIGHT);
        submitButtonContainer.setSpacing(10.0);

        Separator separator2 = new Separator();
        separator2.setPrefWidth(200.0);

        root.setSpacing(10.0);
        root.getChildren().addAll(
                titleLabel, separator1,
                tourNameText, tourNameTextField,
                descriptionText, descriptionTextField,
                startText, startTextField,
                destinationText, destinationTextField,
                transportTypeText, transportTypeChoiceBox,
                submitButtonContainer,
                separator2,
                feedbackText
        );

        updateChoiceBoxWidth();
        transportTypeChoiceBox.setOnAction(event -> updateChoiceBoxWidth());

        Scene scene = new Scene(root, 300, 440);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    @FXML
    private void updateChoiceBoxWidth() {
        String selectedValue = transportTypeChoiceBox.getSelectionModel().getSelectedItem();
        double newWidth = (selectedValue.length() * 10) + 3;
        System.out.println("length: " + selectedValue.length());
        transportTypeChoiceBox.setPrefWidth(newWidth);
    }

    public void onSubmit()
    {
        if (tourNameTextField.getText() == null ||
                tourNameTextField.getText().isBlank() ||
                tourNameTextField.getText().isEmpty()) {
            feedbackText.setText("Please enter a tourname!");
            return;
        } else if (descriptionTextField.getText() == null ||
                descriptionTextField.getText().isBlank() ||
                descriptionTextField.getText().isEmpty()) {
            feedbackText.setText("Please enter a description!");
            return;
        } else if (startTextField.getText() == null ||
                startTextField.getText().isBlank() ||
                startTextField.getText().isEmpty()) {
            feedbackText.setText("Please enter a start!");
            return;
        } else if (destinationTextField.getText() == null ||
                destinationTextField.getText().isBlank() ||
                destinationTextField.getText().isEmpty()) {
            feedbackText.setText("Please enter a destination!");
            return;
        }

        Tour updatedTour = new Tour(tourNameTextField.getText(), descriptionTextField.getText(), startTextField.getText(), destinationTextField.getText(), transportTypeChoiceBox.getSelectionModel().getSelectedItem());
        ListToursView.getInstance().updateTour(updatedTour, tour.getTourId());

        updateTourView();

        popupWindow.close();
    }

    private void updateTourView()
    {
        ListToursView.getInstance().clearItems();
        ListToursView.getInstance().initList();
        //ShowTourInformationView.getInstance().changeTourInformation(tour.getTourId());
    }
}
