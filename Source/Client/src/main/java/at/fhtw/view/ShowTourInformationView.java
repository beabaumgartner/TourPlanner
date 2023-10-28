package at.fhtw.view;

import at.fhtw.view.popUps.DialogView;
import at.fhtw.view.popUps.UpdateTourPopUpView;
import at.fhtw.viewmodel.ListToursViewModel;
import at.fhtw.viewmodel.ShowTourInformationViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowTourInformationView implements Initializable {
    private static ShowTourInformationViewModel showTourInformationViewModel;
    @FXML
    private Label tourNameLabel;
    @FXML
    private Label startLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label transportTypeLabel;
    @FXML
    private Label distanceLabel;
    @FXML
    private Label estimatedTimeLabel;
    @FXML
    private Label popularityLabel;
    @FXML
    private Label childFriendlinessLabel;
    @FXML
    private Label tourDescriptionLabel;
    @FXML
    private HBox HBoxImage;
    @FXML
    private VBox informationTypeField;
    @FXML
    private Label informationTextField;
    @FXML
    public Button updateButton;
    @FXML
    public Button createTourReportButton;
    @FXML
    private static ImageView imageView = new ImageView();

    public ShowTourInformationView()
    {
        this.showTourInformationViewModel = new ShowTourInformationViewModel();
    }

    public static ShowTourInformationViewModel getInstance()
    {
        if(showTourInformationViewModel == null)
        {
            showTourInformationViewModel = new ShowTourInformationViewModel();
        }
        return showTourInformationViewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle rb){

        imageView.imageProperty().bindBidirectional(showTourInformationViewModel.imageProperty());
        imageView.setFitWidth(280);
        imageView.setFitHeight(280);
        HBoxImage.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        HBoxImage.getChildren().add(imageView);

        tourNameLabel.textProperty().bindBidirectional(showTourInformationViewModel.tourNameProperty());
        startLabel.textProperty().bindBidirectional(showTourInformationViewModel.startProperty());
        destinationLabel.textProperty().bindBidirectional(showTourInformationViewModel.destinationProperty());
        transportTypeLabel.textProperty().bindBidirectional(showTourInformationViewModel.transportTypeProperty());
        distanceLabel.textProperty().bindBidirectional(showTourInformationViewModel.distanceProperty());
        estimatedTimeLabel.textProperty().bindBidirectional(showTourInformationViewModel.estimatedTimeProperty());
        popularityLabel.textProperty().bindBidirectional(showTourInformationViewModel.popularityProperty());
        childFriendlinessLabel.textProperty().bindBidirectional(showTourInformationViewModel.childFriendlinessProperty());
        tourDescriptionLabel.textProperty().bindBidirectional(showTourInformationViewModel.tourDescriptionProperty());
        tourDescriptionLabel.setWrapText(true);
        estimatedTimeLabel.setWrapText(true);

        informationTypeField.visibleProperty().bindBidirectional(showTourInformationViewModel.showInformationProperty());
        informationTextField.visibleProperty().bindBidirectional(showTourInformationViewModel.hideInformationProperty());
        updateButton.setOnAction(event -> onActionUpdateTour());
        createTourReportButton.setOnAction(event -> onActionCreateTourReport());
    }

    public void onActionUpdateTour()
    {
        ListToursViewModel listToursViewModel = ListToursView.getInstance();
        new UpdateTourPopUpView(listToursViewModel.getTour(showTourInformationViewModel.getTourId()), "Update Tour");
    }

    public void onActionCreateTourReport()
    {
        Stage popupWindow = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String filename = "/" + Integer.toString(showTourInformationViewModel.getTourId()) + "_" + ListToursView.getInstance().getTour(showTourInformationViewModel.getTourId()).getName() + "_" + "report.pdf";

        directoryChooser.setTitle("Select a Directory");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedDirectory = directoryChooser.showDialog(popupWindow);

        if (selectedDirectory != null) {
            showTourInformationViewModel.createTourReport(showTourInformationViewModel.getTourId(), selectedDirectory.getPath() + filename);
        } else {
            new DialogView("No directory selected!", "Create Tour Report");
        }
    }
}
