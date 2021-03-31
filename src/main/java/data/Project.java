package data;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Project implements Data{
    private String projectName;
    private List<Task> taskList;
    private LocalDateTime creationDate;

    public Project(String projectName, LocalDateTime creationDate) {
        this.projectName = projectName;
        this.taskList = new ArrayList<>();
        this.creationDate = creationDate;
    }

    public void addTask(Task task)
    {
        taskList.add(task);
        DataHandler.addTask(task);
    }

    public String getName() {
        return projectName;
    }

}
