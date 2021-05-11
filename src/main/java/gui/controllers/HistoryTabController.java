package gui.controllers;

import data.Recording;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import logic.graph.GraphTimeCalculator;
import logic.services.HistoryTabService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private TableColumn<Recording, Integer> durationColumn;

    private MainController mainController;

    private HistoryTabService historyTabService;

    /**
     * Upon initialization it reads required data for populating history overview from each project,
     * task and record object and creates a new data object.
     */
    public void initialize() {
        historyTabService = new HistoryTabService(table, projectColumn, taskColumn, startColumn, durationColumn);
        historyTabService.initializeData();
    }


    /**
     * In order for historyTabController to know its parent controller, main controller passes itself
     * to its subcontrollers.
     * @param main
     */
    public void init(MainController main) {
        mainController = main;
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
     *
     * @param days is the given period to show.
     */

    public void showByTime(int days) {
        if (!historyTabService.getRecords().isEmpty()) {
            List<Recording> copyForComputing = new ArrayList<>(historyTabService.getRecords());
            var calculator = new GraphTimeCalculator(days);
            Map<String, Integer> lastWeekProjectData = calculator.findRecordsByDays(copyForComputing);
            var graphTabController = mainController.getGraphTabController();
            graphTabController.updateGraph(lastWeekProjectData);
        }
    }
}
