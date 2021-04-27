package data.tableview;

public class AutoTrackData {

    private String name;
    private String duration;
    private String initialDate;

    public AutoTrackData(String name, String duration) {
        this.name = name;
        this.duration = duration;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    @Override
    public String toString() {
        return name + " " + duration;
    }
}
