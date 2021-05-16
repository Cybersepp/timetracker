package logic.services.autotrack;


import data.AutoTrackData;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.HashMap;
import java.util.Map;

public class GetAutoDataService extends ScheduledService<ObservableList<AutoTrackData>> {

    private ObservableList<AutoTrackData> helper;
    private Map<String, AutoTrackData> baseList = new HashMap<>();

    public GetAutoDataService(ObservableList<AutoTrackData> helper) {
        this.helper = helper;
    }

    @Override
    protected Task<ObservableList<AutoTrackData>> createTask() {
        return new GetAutoDataTask(helper, baseList, this);
    }

    protected void setBaseList(Map<String, AutoTrackData> baseList) {
        this.baseList = baseList;
    }
}
