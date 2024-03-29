package gui.controllers;

import data.AutoTrackData;
import gui.popups.action.treeItemAction.AddRecordingToProjectPopup;
import gui.tooltips.InformationToolTip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import logic.services.autotrack.GetAutoDataService;

public class AutotrackTabController {

    private HistoryTabController historyTabController;

    private MainController mainController;

    private AutoTrackData selectedItem;

    @FXML
    private TableColumn<AutoTrackData, String> pathColumn;

    @FXML
    private TableColumn<AutoTrackData, Integer> timeColumn;

    @FXML
    private TableView<AutoTrackData> autoTable;

    @FXML
    private Button infoButton;

    ObservableList<AutoTrackData> helper = FXCollections.observableArrayList();


    public void initialize() {
        autoTable.setPlaceholder(new Label("Your closed apps will appear here shortly!"));
        infoButton.setTooltip(new InformationToolTip("Auto tracking\n" +
                        "Application names will appear here with the duration after being closed.\n" +
                        "Right click to add the time spent on an application to a task."));
    }
    public void loadProcesses() {
        startAutoTracking();
    }

    public void showContextMenu() {
        getSelectedItem();
        var contextMenu = new ContextMenu();
        var addToProjectMenuItem = new MenuItem("Add to project");
        contextMenu.getItems().add(addToProjectMenuItem);
        autoTable.setContextMenu(contextMenu);
        addToProjectMenuItem.setOnAction(event -> {
            if (selectedItem != null) {
                new AddRecordingToProjectPopup(selectedItem, mainController, autoTable).popup();
            }
        });
    }

    public void init(MainController mainController) {
        this.mainController = mainController;
        historyTabController = mainController.getHistoryTabController();
    }

    /**
     * Method startAutoTracking creates!
     */
    public void startAutoTracking() {
        configureColumns(pathColumn, timeColumn);
        final GetAutoDataService service = new GetAutoDataService(helper);
        service.setPeriod(Duration.millis(5000));
        autoTable.itemsProperty().bind(service.lastValueProperty());
        service.start();
    }

    private void configureColumns(TableColumn<AutoTrackData, String> pathColumn, TableColumn<AutoTrackData, Integer> timeColumn) {
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    public void getSelectedItem() {
        selectedItem = autoTable.getSelectionModel().getSelectedItem();
    }

}


