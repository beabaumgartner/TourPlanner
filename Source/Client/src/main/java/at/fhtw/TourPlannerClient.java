package at.fhtw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TourPlannerClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(TourPlannerClient.class.getResource("/at/fhtw/fxml/MainWindow.fxml"));
        primaryStage.setTitle("Tour Planner");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
