package at.fhtw.view;

import at.fhtw.viewmodel.ListToursViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class SearchView {

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

    @FXML
    private void initialize() {
        searchButton.setText("Search");
        searchButton.setOnAction(event -> loadData());
        searchButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
        searchField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loadData();
            }
        });
    }

    private void loadData() {
        ListToursViewModel toursViewModel = ListToursView.getInstance();

        if (searchField.getText() == null || searchField.getText().isBlank() || searchField.getText().isEmpty()) {
            showAll();
            return;
        }

        reload();
        toursViewModel.filterList(searchField.getText());
    }

    public void reload() {
        ListToursView.getInstance().clearItems();
        ShowTourInformationView.getInstance().hideInformation();
        ShowTourLogsView.getInstance().hideTourLogs();
    }

    public void showAll() {
        ListToursView.getInstance().clearItems();
        ListToursView.getInstance().initList();
        ShowTourInformationView.getInstance().hideInformation();
        ShowTourLogsView.getInstance().hideTourLogs();
    }
}
