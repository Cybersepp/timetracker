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
import java.util.List;
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

    private void initializeTreeView(TreeItem<String> root) {
        root.getChildren().addAll(activeRoot, archivedRoot);

        treeView.setShowRoot(false);
        treeView.setRoot(root);
        treeView.setCellFactory(p -> new TreeCellFactory());
        treeView.setEditable(false);

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

        Map<String, List<ProjectTreeItem>> dataMap = FileAccess.getData();

        if (dataMap != null) {

            List<ProjectTreeItem> projects = new ArrayList<>(dataMap.get("projects"));

            for (ProjectTreeItem project : projects) {
                if (project.isArchived()) {
                    archivedRoot.addJunior(project);
                    continue;
                }
                activeRoot.addJunior(project);
            }
        }
    }

    public AbstractTreeItem selectItem() {
        return (AbstractTreeItem) treeView.getSelectionModel().getSelectedItem();
    }
}
