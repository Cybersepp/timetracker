package logic.graph;

import data.RecordEntryData;
import gui.controllers.HistoryTabController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GraphTimeCalculator {

    private int days;
    private Date initialDate;
    private Comparator<RecordEntryData> comparator = Comparator.comparing(RecordEntryData::getStart);

    public GraphTimeCalculator(int days, Date initialDate) {
        this.days = days;
        this.initialDate = initialDate;
    }

    public Map<String, Integer> findRecordsByDays(ObservableList<RecordEntryData> records) throws ParseException {

        FXCollections.sort(records, comparator.reversed());
        Map<String, Integer> projectData = new HashMap<>();
        for (RecordEntryData record : records) {
            Date recordDate = record.getDate();

            long diffInMillies = Math.abs(initialDate.getTime() - recordDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (diff > days) {
                break;
            }
            projectData.put(record.getProjectName(), record.getDurationInSec());
        }
        return projectData;
    }
}
