package at.fhtw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;


@ExtendWith(ApplicationExtension.class)
class TourPlannerClientTest {
    private Parent root = null;

    /*@Start
    private void start(Stage primaryStage) {
        try {
            root = FXMLLoader.load(TourPlannerClient.class.getResource("/at/fhtw/fxml/MainWindow.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        primaryStage.setTitle("Tour Planner");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }*/



   /*@Test
    void addTourTest(FxRobot robot)
    {
        Button showAddTour = robot.lookup("#reloadButton").query();
        //MenuBar menuBar = robot.lookup(".menubar").query();
        //Button showAddTour = robot.lookup("#onAddTourAction").query();
        Menu fileMenu = menuBar.getMenus().stream()
                .filter(menu -> menu.getText().equals("miExport"))
                .findFirst().orElse(null);
        //robot.clickOn(showAddTour);
    }*/

    /*@Test
    void summarizeReportTest()
    {
        assertDoesNotThrow(() -> {
            BusinessLogic businessLogic = new BusinessLogic();
            businessLogic.createSummarizeReport("/");
        });
    }*/
}