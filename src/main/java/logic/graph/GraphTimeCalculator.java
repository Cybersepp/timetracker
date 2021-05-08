package logic.graph;

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
    public Map<String, Integer> findRecordsByDays(List<Recording> recordings) throws ParseException {

        recordings.sort(comparator.reversed());
        Map<String, Integer> projectData = new HashMap<>();
        final var initialDate = recordings.get(0).getDate();

        for (Recording recording : recordings) {
            var recordDate = recording.getDate();
            long diffInMillis = Math.abs(initialDate.getTime() - recordDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

            if (diff > days) {
                break;
            }
            
            int time = projectData.getOrDefault(recording.getParentProject().getValue(), 0);
            projectData.put(recording.getParentProject().getValue(), recording.getDurationInSec() + time);
        }
        return projectData;
    }
}
