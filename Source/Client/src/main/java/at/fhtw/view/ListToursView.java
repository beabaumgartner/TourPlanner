package at.fhtw.view;

import at.fhtw.models.TourListEntry;
import at.fhtw.view.popUps.DeleteTourMessageView;
import at.fhtw.view.popUps.DialogView;
import at.fhtw.viewmodel.ListToursViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ListToursView implements Initializable {
    private static ListToursViewModel listToursViewModel;
    @FXML
    public TableView tableView = new TableView<>();
    @FXML
    private VBox dataContainer;

    public ListToursView()
    {
        listToursViewModel = new ListToursViewModel();
    }

    public static ListToursViewModel getInstance()
    {
        if(listToursViewModel == null)
        {
            listToursViewModel = new ListToursViewModel();
        }
        return listToursViewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle rb){

        tableView.setItems(listToursViewModel.getTourListItems());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory("tourId"));
        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory("name"));
        tableView.getColumns().addAll(id, name);

        dataContainer.getChildren().add(tableView);
        listToursViewModel.initList();

        tableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    ShowTourInformationView.getInstance().changeTourInformation(((TourListEntry) tableView.getSelectionModel().getSelectedItem()).getTourId());
                    loadTourLogs();
                }
            }
        });
    }

    public void loadTourLogs()
    {
          ShowTourLogsView.getInstance().setTourListEntry((TourListEntry) tableView.getSelectionModel().getSelectedItem());
          ShowTourLogsView.getInstance().showTourLogs(((TourListEntry) tableView.getSelectionModel().getSelectedItem()).getTourId());
    }

    public void onReload(ActionEvent actionEvent) {
        listToursViewModel.clearItems();
        listToursViewModel.initList();
        ShowTourInformationView.getInstance().hideInformation();
        ShowTourLogsView.getInstance().hideTourLogs();
    }

    public void onDeleteTour(ActionEvent actionEvent)
    {
        if(tableView.getSelectionModel().getSelectedItem() != null) {
            TourListEntry tourListEntry = (TourListEntry) tableView.getSelectionModel().getSelectedItem();
            new DeleteTourMessageView(tourListEntry, "Delete Tour");
        }
        else
        {
            new DialogView("Please select a tour!", "Delete Tour");
        }
    }
}
