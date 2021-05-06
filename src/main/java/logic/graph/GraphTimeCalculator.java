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

    public Map<String, Integer> findRecordsByDays(List<Recording> recordings) throws ParseException {

        recordings.sort(comparator.reversed());
        Map<String, Integer> projectData = new HashMap<>();
        final Date initialDate = recordings.get(0).getDate();

        for (Recording recording : recordings) {
            Date recordDate = recording.getDate();
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
