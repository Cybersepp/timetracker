package gui.controllers;

import data.AutoTrackData;
import gui.popups.action.treeItemAction.AddRecordingToProjectPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import logic.services.autotrack.GetAutoDataService;

import java.util.HashMap;
import java.util.Map;

public class AutotrackTabController {

    @FXML
    private Button autotrackButton;

    @FXML
    private TableColumn<AutoTrackData, String> pathColumn;

    @FXML
    private TableColumn<AutoTrackData, Integer> timeColumn;

    @FXML
    private TableView<AutoTrackData> autoTable;

    private MainController mainController;

    private HistoryTabController historyTabController;


    private Map<String, AutoTrackData> newList;
    private Map<String, AutoTrackData> baseList = new HashMap<>();
    ObservableList<AutoTrackData> helper = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    }

    public void loadProcesses() {
        startAutoTracking();
    }

    public void showContextMenu() {
        AutoTrackData selectedItem = autoTable.getSelectionModel().getSelectedItem();
        var contextMenu = new ContextMenu();
        var addToProjectMenuItem = new MenuItem("Add to project");
        contextMenu.getItems().add(addToProjectMenuItem);
        autoTable.setContextMenu(contextMenu);
        addToProjectMenuItem.setOnAction(event -> {
            if (selectedItem != null) {
                new AddRecordingToProjectPopup(selectedItem, mainController, autoTable).popup();
                historyTabController.showByTime(Integer.MAX_VALUE, mainController.getGraphTabController());
            }
        });
    }

    public void init(MainController mainController) {
        this.mainController = mainController;
        historyTabController = mainController.getHistoryTabController();
    }

    public void startAutoTracking() {
        configureColumns(pathColumn, timeColumn);
        final GetAutoDataService service = new GetAutoDataService(helper);
        service.setPeriod(Duration.millis(5000));
        autoTable.itemsProperty().bind(service.valueProperty());
        autoTable.itemsProperty().bind(service.lastValueProperty());
        service.start();
    }

    private void configureColumns(TableColumn<AutoTrackData, String> pathColumn, TableColumn<AutoTrackData, Integer> timeColumn) {
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

}


