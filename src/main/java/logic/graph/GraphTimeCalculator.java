package logic.graph;

import data.tableview.RecordEntryData;
import data.Recording;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GraphTimeCalculator {

    private final int days;
    private final Comparator<Recording> comparator = Comparator.comparing(Recording::getRecordStart);

    public GraphTimeCalculator(int days) {
        this.days = days;
    }

    /**
     * Method for finding the recordings before a certain amount of days
     * @param recordings - List of all the recordings
     * @return - Returns a Map with each project with the time spent on the project
     * @throws ParseException - Throws ParseException if parsing the Date from the Recording class fails
     */
    public Map<String, Integer> findRecordsByDays(List<Recording> recordings) {

        recordings.sort(comparator.reversed());
        Map<String, Integer> projectData = new HashMap<>();
        var initialDate;
        try {
            initialDate = records.get(0).getDate();
        } catch (ParseException e) {
            initialDate = new Date();
        }

        for (Recording recording : recordings) {
            var recordDate = null;
            try {
                recordDate = recording.getDate();
            } catch (ParseException e) {
                continue;
            }
            long diffInMillies = Math.abs(initialDate.getTime() - recordDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (diff > days) {
                break;
            }
            
            int time = projectData.getOrDefault(recording.getParentProject().getValue(), 0);
            projectData.put(recording.getParentProject().getValue(), recording.getDurationInSec() + time);
        }
        return projectData;
    }
}
