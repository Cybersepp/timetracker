package logic.services;

import data.FileAccess;
import data.Recording;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import logic.commands.delete.DeleteProjectCommand;
import logic.commands.delete.DeleteTaskCommand;
import logic.treeItems.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectTabService implements Service{

    private final RootTreeItem activeRoot;
    private final RootTreeItem archivedRoot;
    private final TreeView<String> treeView;

    public ProjectTabService(RootTreeItem activeRoot, RootTreeItem archivedRoot, TreeView<String> treeView) {
        this.activeRoot = activeRoot;
        this.archivedRoot = archivedRoot;
        this.treeView = treeView;
    }

    //TODO remake this f-word thing later to use ProjectTreeItem and TaskTreeItem as a Data Class
    private void initializeProjects() {
        Map<String, Map<String, Object>> dataMap = FileAccess.getProjectData();

        if (dataMap != null) {

            dataMap.forEach((name, projectMap) -> {
                ProjectTreeItem project = new ProjectTreeItem(name);
                activeRoot.addJunior(project);
                // GOOD

                for (Map.Entry<String, Object> entry : projectMap.entrySet()) {
                    String projectAttr = entry.getKey();
                    Object value = entry.getValue();
                    switch (projectAttr) {
                        case "isArchived":
                            if ((boolean) value) {
                                project.setArchived(archivedRoot);
                            }
                            break;
                        case "Tasks":
                            initializeTasks(project, (HashMap<String, Object>) value);
                            break;
                    }
                }
            });

        }
    }
    private void initializeTasks(ProjectTreeItem projectTreeItem, HashMap<String, Object> tasks) {
        tasks.forEach((task, taskInfo) -> {
            var taskItem = new TaskTreeItem(task);
            HashMap<String, Object> taskHash = (HashMap<String, Object>) taskInfo;

            taskHash.forEach((taskAttr, value) -> {
                switch (taskAttr) {
                    case "isDone":
                        if ((boolean) value) {
                            taskItem.setDone(true);
                            break;
                        }
                        taskItem.setDone(false);
                        break;
                    case "Records":
                        //TODO could be optimized if the json file would be changed a bit
                        var recordingInfo = (ArrayList<String>) value;
                        var recordings = new ArrayList<Recording>();
                        for (String info : recordingInfo) {
                            var split = info.split(", ");
                            var recording = new Recording(taskItem, split[0], split[1], Integer.parseInt(split[2]));
                            recordings.add(recording);
                        }

                        taskItem.getRecordings().addAll(recordings);
                        break;
                    default:
                }
            });
            projectTreeItem.addJunior(taskItem);

        });
    }

    private void initializeTreeView(TreeItem<String> root) {
        root.getChildren().addAll(activeRoot, archivedRoot);

        treeView.setShowRoot(false);
        treeView.setRoot(root);
        treeView.setCellFactory(p -> new TreeCellFactory());

        activeRoot.setExpanded(true);
        archivedRoot.setExpanded(false);

        treeView.setOnKeyPressed(this::treeViewOnDelPressed);
    }

    private void treeViewOnDelPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            if (selectItem().getClass() == ProjectTreeItem.class) {
                new DeleteProjectCommand((ProjectTreeItem) selectItem()).commandControl();
            } else if (selectItem().getClass() == TaskTreeItem.class) {
                new DeleteTaskCommand((TaskTreeItem) selectItem()).commandControl();
            }
        }
    }

    public void initializeData(TreeItem<String> root) {
        initializeTreeView(root);
        initializeProjects();
    }

    public AbstractTreeItem selectItem() {
        return (AbstractTreeItem) treeView.getSelectionModel().getSelectedItem();
    }
}
