module tourplanner {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires org.controlsfx.controls;
    requires org.apache.logging.log4j;
    requires kernel;
    requires layout;
    requires io;

    opens at.fhtw to javafx.graphics, javafx.fxml;
    exports at.fhtw;
    exports at.fhtw.view;
    opens at.fhtw.view to javafx.fxml, javafx.graphics;
    exports at.fhtw.viewmodel;
    opens at.fhtw.viewmodel to javafx.fxml, javafx.graphics;
    exports at.fhtw.businessLogic;
    opens at.fhtw.businessLogic to javafx.graphics, javafx.fxml;
    exports at.fhtw.services;
    opens at.fhtw.services to javafx.graphics, javafx.fxml;
    exports at.fhtw.models;
    opens at.fhtw.models to javafx.graphics, javafx.fxml;
    opens at.fhtw.view.popUps to javafx.fxml, javafx.graphics;
    exports at.fhtw.view.popUps;
}