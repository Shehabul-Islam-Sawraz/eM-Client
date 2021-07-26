module eM.Client {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;

    opens sample;
    opens Controller to javafx.fxml;
}