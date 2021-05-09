package logic.graph;

import data.tableview.RecordEntryData;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GraphTimeCalculator {

    private final int days;
    private final Comparator<RecordEntryData> comparator = Comparator.comparing(RecordEntryData::getStart);

    public GraphTimeCalculator(int days) {
        this.days = days;

    }

    public Map<String, Integer> findRecordsByDays(List<RecordEntryData> records)  {

        Collections.sort(records, comparator.reversed());
        Map<String, Integer> projectData = new HashMap<>();
        Date initialDate;
        try {
            initialDate = records.get(0).getDate();
        } catch (ParseException e) {
            initialDate = new Date();
        }

        for (RecordEntryData record : records) {
            Date recordDate = null;
            try {
                recordDate = record.getDate();
            } catch (ParseException e) {
                continue;
            }
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
