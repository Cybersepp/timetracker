package logic.services.graph;

import data.Recording;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, BigDecimal> findRecordsByDays(List<Recording> recordings) {

        recordings.sort(comparator.reversed());
        Map<String, BigDecimal> projectData = new HashMap<>();
        var initialDate = LocalDateTime.now();

        for (Recording recording : recordings) {
            var recordDate = recording.getRecordStartInLocalDateTime();
            long diffInDays = Duration.between(recordDate, initialDate).toDays();

            if (diffInDays > days) {
                break;
            }

            //calculations for hours
            BigDecimal previousTime = projectData.getOrDefault(recording.getParentProject().getValue(), new BigDecimal(0));
            BigDecimal newTime = new BigDecimal(recording.getDurationInSec());
            newTime = newTime.divide(BigDecimal.valueOf(3600), 10, RoundingMode.HALF_UP);

            projectData.put(recording.getParentProject().getValue(), previousTime.add(newTime));
        }
        return projectData;
    }
}
