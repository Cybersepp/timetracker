module TimeTrack {
  requires javafx.controls;
  requires javafx.fxml;
    requires json.simple;
    opens gui to javafx.fxml;
  exports gui;
}
