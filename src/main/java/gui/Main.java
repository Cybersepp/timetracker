package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
    primaryStage.setTitle("timetrack");
    primaryStage.show();
    primaryStage.setMinHeight(670);
    primaryStage.setMinWidth(970);
    primaryStage.setScene(new Scene(root));
  }

  public static void main(String[] args) {
    launch(args);
  }
}
