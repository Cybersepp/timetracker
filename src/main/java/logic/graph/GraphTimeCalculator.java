package logic.graph;

import data.RecordEntryData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GraphTimeCalculator {

    private final int days;
    private final Comparator<RecordEntryData> comparator = Comparator.comparing(RecordEntryData::getStart);

    public GraphTimeCalculator(int days) {
        this.days = days;

    }

    public Map<String, Integer> findRecordsByDays(List<RecordEntryData> records) throws ParseException {

        Collections.sort(records, comparator.reversed());
        Map<String, Integer> projectData = new HashMap<>();
        final Date initialDate = records.get(0).getDate();

        for (RecordEntryData record : records) {
            Date recordDate = record.getDate();
            long diffInMillies = Math.abs(initialDate.getTime() - recordDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (diff > days) {
                break;
            }
            
            int time = projectData.getOrDefault(record.getProjectName(), 0);
            projectData.put(record.getProjectName(), record.getDurationInSec() + time);
        }
        return projectData;
    }
}
