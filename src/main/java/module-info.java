module Main{
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.base;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    opens gui.controllers to javafx.fxml;
    opens data.tableview to javafx.base;
  exports gui;
}
