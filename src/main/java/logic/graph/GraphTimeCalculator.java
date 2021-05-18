package logic.graph;

import data.Recording;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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
     */
    public Map<String, Integer> findRecordsByDays(List<Recording> recordings) {

        recordings.sort(comparator.reversed());
        Map<String, Integer> projectData = new HashMap<>();
        var initialDate = LocalDateTime.now();

        for (Recording recording : recordings) {
            var recordDate = recording.getRecordStartInLocalDateTime();
            long diffInDays = Duration.between(recordDate, initialDate).toDays();

            if (diffInDays > days) {
                break;
            }
            
            int time = projectData.getOrDefault(recording.getParentProject().getValue(), 0);
            projectData.put(recording.getParentProject().getValue(), (recording.getDurationInSec() + time));
        }
        return projectData;
    }
}
