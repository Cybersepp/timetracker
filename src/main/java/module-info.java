module Main {
  requires javafx.controls;
  requires javafx.fxml;
    requires json.simple;
    opens gui.controllers to javafx.fxml;
  exports gui;
}
