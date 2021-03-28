package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TimeTrack extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
    primaryStage.setTitle("timetrack");
    primaryStage.show();
    primaryStage.setScene(new Scene(root));
  }


  public static void main(String[] args) {
    launch(args);
  }
}
