module eM.Client {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    opens sample;
    opens Controller to javafx.fxml;
}