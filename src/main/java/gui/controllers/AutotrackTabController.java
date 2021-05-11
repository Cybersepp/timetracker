package gui.controllers;

import data.AutoTrackData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import logic.services.AutotrackTabService;

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

    private AutotrackTabService autotrackTabService;

    @FXML
    public void initialize() {
        autotrackTabService = new AutotrackTabService(autoTable);
    }

    public void loadProcesses() {
        autotrackTabService.loadProcesses(pathColumn, timeColumn);
    }

    public void showContextMenu() {
        autotrackTabService.showContextMenu(mainController, historyTabController);
    }

    public void init(MainController mainController) {
        this.mainController = mainController;
        historyTabController = mainController.getHistoryTabController();
    }

}


