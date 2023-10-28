package at.fhtw.view;

import at.fhtw.TourPlannerClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class AddTourViewTest extends Application {
    private Parent root;
    private ResourceBundle resourceBundle;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(TourPlannerClient.class.getResource("/at/fhtw/fxml/MainWindow.fxml"));
        primaryStage.setTitle("Tour Planner");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }

    @Test
    void addTourTest(FxRobot robot)
    {
        Button showAddTour = robot.lookup("#onAddTourAction").query();
        robot.clickOn(showAddTour);
        assertTrue(robot.lookup("#tourCreationVbox").query().isVisible());

        TextField tourName = robot.lookup("#inputTourName").query();
        robot.clickOn(tourName).write("TourTestName");
        TextField tourFrom = robot.lookup("#inputTourFrom").query();
        robot.clickOn(tourFrom).write("Wien");
        TextField tourTo = robot.lookup("#inputTourTo").query();
        robot.clickOn(tourTo).write("Graz");
        Button submitAddTour = robot.lookup("#submitAddTour").query();
        robot.clickOn(submitAddTour);
        robot.sleep(500);
        Text errorText = robot.lookup("#errorAddTour").query();
        String errorTextValue = errorText.getText();
        assertFalse(errorTextValue.equals("An error occurred."));
    }
}