package gui.controllers;

import data.tableview.AutoTrackData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class AutotrackTabController {


    @FXML
    private Button autotrackButton;

    @FXML
    private TableColumn<AutoTrackData, String> pathColumn;

    @FXML
    private TableColumn<AutoTrackData, String> timeColumn;

    @FXML
    private TableView<AutoTrackData> autoTable;

    private Collection<AutoTrackData> baseList;

    private Collection<AutoTrackData> newList;

    ObservableList<AutoTrackData> helper = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
    }

    public void configureColumns() {
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    public void loadProcesses() throws Exception {

        String username = System.getProperty("user.name");

        try {
            if (baseList == null) {
                baseList = new ArrayList();
            }
            String line;
            Process p = Runtime.getRuntime().exec("ps -u " + username);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = input.readLine()) != null) {
                String[] data = line.trim().replaceAll(" +", " ").split(" ");
                helper.add(new AutoTrackData(data[3], data[2]));
            }
            input.close();

            baseList = new HashSet<>(helper);
            configureColumns();
            autoTable.setItems(helper);
        } catch (Exception err) {
            throw new Exception(err);
        }
    }
}
