package at.fhtw.view.popUps;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogView extends Dialog<Void> {
    String message;
    String title;
    public DialogView(String message, String title) {
        super();
        this.message = message;
        this.title = title;

        initialize();
    }

    public void initialize() {

        Stage popupWindow = new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);
        popupWindow.setMinWidth(300);
        popupWindow.setMinHeight(100);

        Label label = new Label(message);
        Button okButton= new Button("OK");
        okButton.setPrefWidth(75);
        label.setAlignment(Pos.CENTER);

        okButton.setOnAction(e -> popupWindow.close());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(16, 16, 30, 16));
        layout.setMaxWidth(500);
        layout.setMinWidth(300);
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label, okButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);

        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }
}
