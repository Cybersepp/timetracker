package logic.services.autotrack;

import data.AutoTrackData;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.time.LocalTime;
import java.util.*;

public class GetAutoDataTask extends Task<ObservableList<AutoTrackData>> {

    private Map<String, AutoTrackData> newList;
    private Map<String, AutoTrackData> baseList;
    private ObservableList<AutoTrackData> helper;
    private final GetAutoDataService service;


    public GetAutoDataTask(ObservableList<AutoTrackData> helper, Map<String, AutoTrackData> baseList,
                           GetAutoDataService service) {
        this.helper = helper;
        this.baseList = baseList;
        this.service = service;
    }

    @Override
    protected ObservableList<AutoTrackData> call() {
        newList = new HashMap<>();
        Optional<String> currUser = ProcessHandle.current().info().user();
        ProcessHandle.allProcesses()
                .filter(p1 -> p1.info().user().equals(currUser))
                .forEach(p2 -> showProcess(p2, newList));
        if (baseList.isEmpty()) {
            baseList.putAll(newList);
        }
        Map<String, AutoTrackData> compareMap = new HashMap<>(baseList);
        for (String oldKey : baseList.keySet()) {
            for (String newKey : newList.keySet()) {
                if (oldKey.equals(newKey)) {
                    compareMap.remove(oldKey);
                }
            }
        }
        if (compareMap.isEmpty()) {
            service.setBaseList(newList);
            return helper;
        }
        List<AutoTrackData> conversionList = new ArrayList<>(compareMap.values());
        helper.addAll(conversionList);
        service.setBaseList(newList);
        return helper;
    }
    private void showProcess(ProcessHandle ph, Map<String, AutoTrackData> newList) {
        ProcessHandle.Info info = ph.info();
        if (newList.containsKey(info.command().toString())) {
            LocalTime duration = newList.get(info.command().toString()).getDuration();
            if (info.totalCpuDuration().isPresent()) {
                duration = duration.plusSeconds(info.totalCpuDuration().get().toSeconds());
                duration = duration.plusMinutes(info.totalCpuDuration().get().toMinutes());
                duration = duration.plusHours(info.totalCpuDuration().get().toHours());
                newList.get(info.command().toString()).setDuration(duration);
            }
        } else {
            LocalTime duration = LocalTime.of(info.totalCpuDuration().get().toHoursPart(),
                    info.totalCpuDuration().get().toMinutesPart(), info.totalCpuDuration().get().toSecondsPart());
            newList.put(info.command().toString(), new AutoTrackData(info.command().orElse("none"), duration));
        }
    }
}
