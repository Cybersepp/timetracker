module Main{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    opens gui.controllers to javafx.fxml;
    opens data to javafx.base;
    exports gui;
    exports logic.treeItems;
    exports data;
    exports data.deserialization;
}
