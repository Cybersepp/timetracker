package gui.treeItems;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class AbstractTreeItem extends TreeItem<String> {

    public abstract ContextMenu getMenu();

    protected MenuItem changeName() {
        MenuItem changeName = new MenuItem("change name");
        changeName.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO set name to a new name
                // TODO find a way to change project / task name and keep it's data intact (tasks, time spent)
            }
        });
        return changeName;
    }

    protected MenuItem createItem(String type) {
        if (type.equals("project")) return createProject();
        else return createTask();
    }

    private MenuItem createProject() {
        MenuItem addProject = new MenuItem("Create project");
        addProject.setOnAction(e -> popup("project"));
        /* addProject.setOnAction(new EventHandler() {
            public void handle(Event t) {
                ProjectTreeItem newProject = new ProjectTreeItem("Return CCCP to former glory");
                getChildren().add(newProject);
            }
        }); */
        return addProject;
    }

    private MenuItem createTask() {
        MenuItem addTask = new MenuItem("add task");
        addTask.setOnAction(e -> popup("task"));
        return addTask;
    }

    protected MenuItem deleteItem(String text) {
        MenuItem deleteTask = new MenuItem(text);
        deleteTask.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO should send out a warning message if there are any recordings associated with the item
                // TODO should also delete all history that is associated with this item
                // TODO Maybe should have also delete shortcut?
            }
        });
        return deleteTask;
    }

    protected MenuItem archive() {
        MenuItem archive = new MenuItem("archive project");
        archive.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO replace active project to archived project without losing any daya
                // TODO archived projects are unable to start recordings
            }
        });
        return archive;
    }

    protected MenuItem unarchive() {
        MenuItem unarchive = new MenuItem("unarchive");
        unarchive.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO replace archived project to active project without losing any data
                // TODO unarchived projects should have done tasks intact and all data intact
            }
        });
        return unarchive;
    }

    protected MenuItem markAsDone() {
        MenuItem markAsDone = new MenuItem("mark as done");
        markAsDone.setOnAction(new EventHandler() {
            public void handle(Event t) {
                // TODO mark task as done and cross it out
                // TODO done tasks should not be able to add any recordings
                // TODO crossed out tasks should be at the end of the project's task list
            }
        });
        return markAsDone;
    }

    public void popup(String type) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        Label label = createLabel(type);
        TextField textField = new TextField();
        Button createButton = createButton(window, type, textField);
        Button cancelButton = createCancelButton(window, "Cancel");

        VBox display = new VBox(10);
        display.getChildren().addAll(label, textField, createButton, cancelButton);
        display.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(display, 300, 250);
        window.setScene(scene1);
        window.showAndWait();
    }

    private Label createLabel(String type) {
        Label label = new Label("Name your " + type);
        label.setFont(Font.font ("Verdana", 16));
        return label;
    }

    private Button createCancelButton(Stage stage, String name) {
        Button button = new Button(name);
        button.setFont(Font.font ("Verdana", 14));
        button.setOnAction(e -> stage.close());
        return button;
    }

    private Button createButton(Stage stage, String type, TextField textField) {
        stage.setTitle("Create a " + type);
        Button button = new Button("Create " + type);
        button.setFont(Font.font ("Verdana", 14));
        button.setStyle("-fx-background-color: #00B5FE");
        button.setOnAction(e -> {
            if (type.equals("project")) {
                createProjectBranch(textField);
            }
            if (type.equals("task")) {
                createTaskLeaf(textField);
            }
            stage.close();
        });
        return button;
    }

    private void createProjectBranch(TextField textField){
        ProjectTreeItem newProject = new ProjectTreeItem(textField.getText());
        getChildren().add(newProject);
    }

    private void createTaskLeaf(TextField textField) {
        TaskTreeItem newTask = new TaskTreeItem(textField.getText());
        getChildren().add(newTask);
    }
}
