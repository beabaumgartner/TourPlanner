package at.fhtw.view.popUps;

import at.fhtw.models.TourLog;
import at.fhtw.view.ShowTourLogsView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateTourLogPopUpView extends Dialog<Void> {
    private Integer tourId;
    private String title;
    @FXML
    private Stage popupWindow = new Stage();
    @FXML
    private Label feedbackText = new Label();
    @FXML
    private Text totalTimeText = new Text();
    @FXML
    private HBox totalTimeContainer = new HBox();
    @FXML
    private TextField daysTextField = new TextField();
    @FXML
    private TextField hoursTextField = new TextField();
    @FXML
    private TextField minutesTextField = new TextField();
    @FXML
    private Label difficultyLabel = new Label();
    @FXML
    private HBox difficultyStarsContainer = new HBox();
    @FXML
    private Label ratingLabel = new Label();
    @FXML
    private HBox ratingStarsContainer = new HBox();
    @FXML
    private TextArea commentTextField = new TextArea();
    @FXML
    private HBox submitButtonContainer = new HBox();
    private String defaultStylesheet = "file:src/main/resources/at/fhtw/css_sheets/application.css";
    private int selectedDifficultyStars = 0;
    private static final int NUM_STARS = 5;
    private int selectedRatingStars = 0;
    private static final String FILLED_STAR = "★";
    private static final String UNFILLED_STAR = "☆";
    public CreateTourLogPopUpView(Integer tourId, String title) {
        super();
        this.tourId = tourId;
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

        Label titleLabel = new Label("Create Tour Log");
        titleLabel.setFont(new Font(14.0));

        Separator separator1 = new Separator();
        separator1.setPrefWidth(200.0);

        //create input fields
        createTotalInputField();
        createDifficultyInputField();
        createRatingInputField();
        Text commentText = new Text("Comment");
        createSubmitButtons();

        Separator separator2 = new Separator();
        separator2.setPrefWidth(200.0);

        root.setSpacing(10.0);
        root.getChildren().addAll(
                titleLabel, separator1,
                totalTimeText, totalTimeContainer,
                difficultyLabel, difficultyStarsContainer,
                ratingLabel, ratingStarsContainer,
                commentText, commentTextField,
                submitButtonContainer,
                separator2,
                feedbackText
        );

        Scene scene = new Scene(root, 300, 400);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    public void onSubmit()
    {
        hoursTextField.getStyleClass().remove("feedbackText");;
        minutesTextField.getStyleClass().remove("feedbackText");;

        try {
            if (daysTextField.getText() == null ||
                    daysTextField.getText().isBlank() ||
                    daysTextField.getText().isEmpty()) {
                feedbackText.setText("Please enter a Total Time!");
                return;
            } else if (hoursTextField.getText() == null ||
                    hoursTextField.getText().isBlank() ||
                    hoursTextField.getText().isEmpty()) {
                feedbackText.setText("Please enter a Total Time!");
                return;
            } else if (minutesTextField.getText() == null ||
                    minutesTextField.getText().isBlank() ||
                    minutesTextField.getText().isEmpty()) {
                feedbackText.setText("Please enter a Total Time!");
                return;
            } else if (Integer.parseInt(hoursTextField.getText()) > 24) {
                feedbackText.setText("Invalid time format");
                hoursTextField.getStyleClass().add("feedbackText");
                return;
            } else if (Integer.parseInt(minutesTextField.getText()) > 60) {
                feedbackText.setText("Invalid time format");
                minutesTextField.getStyleClass().add("feedbackText");
                return;
            } else if(selectedDifficultyStars < 1 || selectedDifficultyStars > 5)
            {
                feedbackText.setText("Please enter a difficulty between 1 and 5");
                return;
            } else if(selectedRatingStars < 1 || selectedRatingStars > 5)
            {
                feedbackText.setText("Please enter a rating between 1 and 5");
                return;
            } else if (commentTextField.getText() == null ||
                    commentTextField.getText().isBlank() ||
                    commentTextField.getText().isEmpty()) {
                feedbackText.setText("Please enter a Comment!");
                return;
            }

            TourLog createdTourLog = new TourLog(commentTextField.getText(),
                    selectedDifficultyStars,
                    ShowTourLogsView.getInstance().calculateTotalTimeFromInput(Integer.parseInt(daysTextField.getText()),
                    Integer.parseInt(hoursTextField.getText()),
                    Integer.parseInt(minutesTextField.getText())),
                    selectedRatingStars);

            ShowTourLogsView.getInstance().createTourLog(tourId, createdTourLog);

            ShowTourLogsView.getInstance().showTourLogs(tourId);

            popupWindow.close();
        } catch (NumberFormatException e) {
            feedbackText.setText("Please enter a number!");
        }
    }

    private void createTotalInputField()
    {
        totalTimeContainer.setSpacing(5);
        daysTextField.setMaxWidth(60);
        hoursTextField.setMaxWidth(60);
        minutesTextField.setMaxWidth(60);
        daysTextField.setPromptText("days");
        hoursTextField.setPromptText("hours");
        minutesTextField.setPromptText("minutes");
        totalTimeText.setText("Total Time");

        totalTimeContainer.getChildren().addAll(daysTextField, hoursTextField, minutesTextField);
    }

    private void createDifficultyInputField()
    {
        difficultyLabel.setText("Difficulty");

        difficultyStarsContainer.setAlignment(Pos.BASELINE_LEFT);
        difficultyStarsContainer.setSpacing(10);

        Label[] difficutlyStars = new Label[NUM_STARS];

        for (int i = 0; i < NUM_STARS; i++) {
            Label star = new Label(UNFILLED_STAR);
            star.getStyleClass().add("star");
            star.setId(String.valueOf(i + 1));
            star.setOnMouseClicked(this::handleDifficultyStarClick);
            difficutlyStars[i] = star;
            difficultyStarsContainer.getChildren().add(star);
        }
    }

    private void createRatingInputField()
    {
        ratingLabel.setText("Rating");

        ratingStarsContainer.setAlignment(Pos.BASELINE_LEFT);
        ratingStarsContainer.setSpacing(10);

        Label[] ratingStars = new Label[NUM_STARS];

        for (int i = 0; i < NUM_STARS; i++) {
            Label star = new Label(UNFILLED_STAR);
            star.getStyleClass().add("star");
            star.setId(String.valueOf(i + 1));
            star.setOnMouseClicked(this::handleRatingStarClick);
            ratingStars[i] = star;
            ratingStarsContainer.getChildren().add(star);
        }
    }

    private void createSubmitButtons()
    {
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
    }

    private void handleDifficultyStarClick(MouseEvent event) {
        Label clickedStar = (Label) event.getSource();
        int rating = Integer.parseInt(clickedStar.getId());

        Label[] stars = ((HBox) clickedStar.getParent()).getChildren().stream()
                .map(node -> (Label) node)
                .toArray(Label[]::new);

        updateStars(stars, rating);
        selectedDifficultyStars = rating;
    }

    private void handleRatingStarClick(MouseEvent event) {
        Label clickedStar = (Label) event.getSource();
        int rating = Integer.parseInt(clickedStar.getId());

        Label[] stars = ((HBox) clickedStar.getParent()).getChildren().stream()
                .map(node -> (Label) node)
                .toArray(Label[]::new);

        updateStars(stars, rating);
        selectedRatingStars = rating;
    }

    private void updateStars(Label[] stars, int rating) {
        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setText(FILLED_STAR);
            } else {
                stars[i].setText(UNFILLED_STAR);
            }
        }
    }
}
