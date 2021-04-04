package data;

import java.util.ArrayList;
import java.util.List;

public abstract class DataHandler {
    private static List<Project> projectList = new ArrayList<>();
    private static List<Task> taskList = new ArrayList<>();
    public static Task currentlyChosenTask;

    // TODO getProjectByName, getTaskByName, checkName are a bit inefficent and can be optimized
    public static Project getProjectByName(String name) {
        for (Project project : projectList) {
            if (project.getName().equals(name)) return project;

        }
        return null;
    }

    public static Task getTaskByName(String name) {
        for (Task task : taskList) {
            if (task.getName().equals(name)) return task;

        }
        return null;
    }

    public static boolean checkName(String name) {
        for (Project project : projectList) {
            if (project.getName().equals(name)) return true;
        }
        return false;
    }

    public static void addProject(Project project) {
        projectList.add(project);
    }

    public static void addTask(Task task) {
        taskList.add(task);
    }

    public static void showProjects() {
        projectList.forEach(t -> System.out.println(t.getName()));
    }

    public static void showCurrentlyChosen() {
        System.out.println(currentlyChosenTask.getName());
    }

    public static List<Project> getProjectList() {
        return projectList;
    }
    public static List<Task> getTaskList() {
        return taskList;
    }
}
