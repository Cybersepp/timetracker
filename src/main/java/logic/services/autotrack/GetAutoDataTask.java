package logic.services.autotrack;

import data.AutoTrackData;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;


public class GetAutoDataTask extends Task<ObservableList<AutoTrackData>> {

    private Map<String, AutoTrackData> newMap; // new map to compare baseMap to
    private final Map<String, AutoTrackData> baseMap; // reference map to compare newMap to
    private final ObservableList<AutoTrackData> helper; // processes that can be shown to user
    private final GetAutoDataService service;


    public GetAutoDataTask(ObservableList<AutoTrackData> helper, Map<String, AutoTrackData> baseMap,
                           GetAutoDataService service) {
        this.helper = helper;
        this.baseMap = baseMap;
        this.service = service;
    }

    /**
     * Call method returns an observable list of ended processes that can be shown to user.
     * Algorithm:
     * 1. Create new map and put all of the currently running user processes in that.
     * 2. Compare newMap with baseMap and look for differences.
     * 3. if newMap doesn't have some process that baseMap had, then add it to the final observable list, because the
     * process is finished.
     * 4. Otherwise continue without changes and now use newly created map as a baseMap.
     * @return ObseravbleList with process information which later will be shown to user.
     */
    @Override
    protected ObservableList<AutoTrackData> call() {
        newMap = new HashMap<>();
        Optional<String> currUser = ProcessHandle.current().info().user();
        ProcessHandle.allProcesses()
                .filter(p1 -> p1.info().user().equals(currUser))
                .forEach(p2 -> showProcess(p2, newMap));
        if (baseMap.isEmpty()) {
            baseMap.putAll(newMap);
        }
        Map<String, AutoTrackData> compareMap = new HashMap<>(baseMap);
        for (String oldKey : baseMap.keySet()) {
            for (String newKey : newMap.keySet()) {
                if (oldKey.equals(newKey)) {
                    compareMap.remove(oldKey);
                }
            }
        }
        if (compareMap.isEmpty()) {
            service.setBaseList(newMap);
            return helper;
        }
        List<AutoTrackData> conversionList = new ArrayList<>(compareMap.values());
        helper.addAll(conversionList);
        service.setBaseList(newMap);
        return helper;
    }

    /**
     * showProcess method sums up all of the process durations with the same process name, meaning
     * that inaccuracies might be present in the final result if the app is using threads that run simultaneously
     * and each thread is regarded as a separate process by the user's OS.
     * @param ph ProcessHandle
     * @param newMap Map with processes.
     */
    private void showProcess(ProcessHandle ph, Map<String, AutoTrackData> newMap) {
        ProcessHandle.Info info = ph.info();
        String appName = "unknown";
        if (newMap.containsKey(info.command().toString())) {
            LocalTime duration = newMap.get(info.command().toString()).getDuration();
            if (info.totalCpuDuration().isPresent()) {
                duration = duration.plusSeconds(info.totalCpuDuration().get().toSeconds());
                duration = duration.plusMinutes(info.totalCpuDuration().get().toMinutes());
                duration = duration.plusHours(info.totalCpuDuration().get().toHours());
                newMap.get(info.command().toString()).setDuration(duration);
            }
        } else {
            if (info.totalCpuDuration().isPresent()) {
                var duration = LocalTime.of(info.totalCpuDuration().get().toHoursPart(),
                        info.totalCpuDuration().get().toMinutesPart(), info.totalCpuDuration().get().toSecondsPart());
                if (duration.equals(LocalTime.of(0,0,0)) || duration.equals(LocalTime.MIN)) {
                    return;
                }
                if (info.command().isPresent()) {
                    Path path = Paths.get(info.command().get());
                    String lastSegment = path.getFileName().toString();
                    newMap.put(info.command().toString(), new AutoTrackData(lastSegment, duration));
                } else {
                    newMap.put(appName, new AutoTrackData(appName, duration));
                }

            }
        }
    }
}
