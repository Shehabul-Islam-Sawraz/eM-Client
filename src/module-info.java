module eM.Client {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires java.mail;
    requires activation;

    opens sample;
    opens Controller to javafx.fxml;
    opens Model;
}