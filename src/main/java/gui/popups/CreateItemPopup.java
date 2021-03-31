package gui.popups;

import gui.treeItems.AbstractTreeItem;
import gui.treeItems.ProjectTreeItem;
import gui.treeItems.TaskTreeItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import data.Project;
import data.Task;
import data.DataHandler;

import java.time.LocalDateTime;

public class CreateItemPopup implements Popup{

    public void popup(AbstractTreeItem treeItem, String type) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMaxWidth(450);
        window.setMinHeight(250);
        window.setMaxHeight(375);

        Label label = new Label("Name your " + type);
        label.setFont(Font.font ("Verdana", FontWeight.BOLD, 16));
        TextField textField = new TextField();

        Button createButton = addButton("Create " + type);
        Button cancelButton = addButton("Cancel");
        createButtonFunctionality(treeItem, createButton, window, type, textField);
        cancelButton.setOnAction(event -> window.close());

        VBox display = new VBox(10);
        display.setPadding(new Insets(10, 40, 30, 40));
        display.setSpacing(10);
        display.getChildren().addAll(label, textField, createButton, cancelButton);
        display.setAlignment(Pos.CENTER);
        VBox.setMargin(textField,new Insets(15, 0, 30, 0));

        Scene scene1= new Scene(display, 300, 250);
        window.setScene(scene1);
        window.showAndWait();
    }

    private Button addButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font ("Verdana", 14));
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    private void createButtonFunctionality(AbstractTreeItem treeItem, Button button, Stage stage, String type, TextField textField) {
        stage.setTitle("Create a " + type);
        button.setStyle("-fx-background-color: #00B5FE");
        button.setOnAction(e -> {
            if (type.equals("project")) {
                createProjectBranch(treeItem, textField);
            }
            if (type.equals("task")) {
                createTaskLeaf(treeItem, textField);
            }
            stage.close();
        });
    }

    private void createProjectBranch(AbstractTreeItem treeItem, TextField textField){
        String projectName = textField.getText();
        ProjectTreeItem newProject = new ProjectTreeItem(projectName);
        treeItem.getChildren().add(newProject);

        Project projectObj = new Project(projectName, LocalDateTime.now());
        DataHandler.addProject(projectObj);
        DataHandler.showProjects();

    }

    private void createTaskLeaf(AbstractTreeItem treeItem, TextField textField) {
        String taskName = textField.getText();
        TaskTreeItem newTask = new TaskTreeItem(textField.getText());
        treeItem.getChildren().add(newTask);

        String projectName = treeItem.getValue();
        Task taskObj = new Task(taskName, LocalDateTime.now(), projectName);
        DataHandler.addTask(taskObj);
        DataHandler.getProjectByName(projectName).addTask(taskObj);

        System.out.println("Task " + taskName + " was added to " + projectName + " project.");
    }
}
