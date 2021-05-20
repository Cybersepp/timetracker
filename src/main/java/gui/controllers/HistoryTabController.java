package gui.controllers;

import data.Recording;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import logic.services.HistoryTabService;

public class HistoryTabController {

    @FXML
    private TableView<Recording> table;

    @FXML
    private TableColumn<Recording, String> projectColumn;

    @FXML
    private TableColumn<Recording, String> taskColumn;

    @FXML
    private TableColumn<Recording, String> startColumn;

    @FXML
    private TableColumn<Recording, String> durationColumn;

    private HistoryTabService historyTabService;

    /**
     * Upon initialization it reads required data for populating history overview from each project,
     * task and record object and creates a new data object.
     */
    public void initialize() {
        historyTabService = new HistoryTabService(table, projectColumn, taskColumn, startColumn, durationColumn);
        historyTabService.initializeData();
        table.setPlaceholder(new Label("No recording history yet!"));
    }

    /**
     * When record is added, history tab is updated and sorted so the user can see the newest record.
     * @param record is data object to be added.
     */
    public void addRecord(Recording record) {
        historyTabService.addRecord(record);
    }

    /**
     * Method showByTime updates the graph for a certain time period in days.
     * Counting in descending order from the method call day.
     * Efficient method, since it sorts list by date and then starts from the initial date.
     * @param days is the given period to show.
     */
    public void showByTime(int days, GraphTabController graphTabController) {
        historyTabService.showByTime(days, graphTabController);
    }

    public void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("History tab information");
        alert.setHeaderText(null);
        alert.setContentText("Interact with items using right click.\n" +
                "Click on the table header to sort the items by that column.");
        alert.showAndWait();
    }
}
