package at.fhtw.view;

import at.fhtw.view.popUps.DialogView;
import at.fhtw.viewmodel.ApplicationViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationView implements Initializable {

    ApplicationViewModel applicationViewModel;

    @FXML
    BorderPane layout;
    @FXML
    HBox HBoxDarkmodeButton;
    @FXML
    Button darkmodeButton;
    @FXML
    Button lightmodeButton;
    private String defaultStylesheet = "file:src/main/resources/at/fhtw/css_sheets/application.css";
    private String darkModeStylesheet = "file:src/main/resources/at/fhtw/css_sheets/darkmode.css";
    private boolean darkModeEnabled;
    SimpleObjectProperty<Stage> stage = new SimpleObjectProperty<>();

    public ApplicationView()
    {
        applicationViewModel = new ApplicationViewModel();
        darkModeEnabled = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void onFileClose(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void onHelpAbout(ActionEvent event) {
        new DialogView("Hello to our wonderful Tour Planner Application!\nHave fun!", "Tour Planner Information");
    }


    public void onExportButton(ActionEvent actionEvent)
    {
        Stage popupWindow = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String filename = "/tours_export";

        directoryChooser.setTitle("Select a Directory");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedDirectory = directoryChooser.showDialog(popupWindow);


        if (selectedDirectory != null) {
            applicationViewModel.exportTours(selectedDirectory.getPath() + filename);

        } else {
            new DialogView("No directory selected!", "Export Tours");
        }
    }

    public void onImportButton(ActionEvent actionEvent)
    {
        Stage popupWindow = new Stage();

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select a File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        //FileChooser.ExtensionFilter textFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.csv");
        File selectedFile = fileChooser.showOpenDialog(popupWindow);
        //fileChooser.getExtensionFilters().addAll(textFilter);

        if (selectedFile != null) {
            applicationViewModel.importTours(selectedFile.getPath());
            reload();
        } else {
            new DialogView("No file selected!", "Import Tours");
        }
    }

    public void onSummarizeButtonButton(ActionEvent actionEvent)
    {
        Stage popupWindow = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String filename = "/summarize_report";

        directoryChooser.setTitle("Select a Directory");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedDirectory = directoryChooser.showDialog(popupWindow);


        if (selectedDirectory != null) {
            applicationViewModel.createSummarizeReport(selectedDirectory.getPath() + filename);

        } else {
            new DialogView("No directory selected!", "Summarize Report");
        }
    }

    public void onTogglDarkmode(ActionEvent actionEvent) {

        lightmodeButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                layout.getStylesheets().clear(); // Clear the existing stylesheets
                layout.getStylesheets().add(defaultStylesheet); // Apply the dark mode stylesheet

                darkmodeButton.setText("Dark");
                lightmodeButton.setText("");
                lightmodeButton.setDisable(true);
                darkmodeButton.setDisable(false);
            }
        });

        darkmodeButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                layout.getStylesheets().clear(); // Clear the existing stylesheets
                layout.getStylesheets().add(darkModeStylesheet); // Apply the dark mode stylesheet

                lightmodeButton.setText("Light");
                darkmodeButton.setText("");

                darkmodeButton.setDisable(true);
                lightmodeButton.setDisable(false);
            }
        });
    }

    public void reload() {
        ListToursView.getInstance().clearItems();
        ListToursView.getInstance().initList();
        ShowTourInformationView.getInstance().hideInformation();
        ShowTourLogsView.getInstance().hideTourLogs();
    }
}
