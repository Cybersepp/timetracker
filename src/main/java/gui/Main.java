package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.commands.CloseAppCommand;

import java.util.Objects;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
    primaryStage.setTitle("timetrack");
    primaryStage.show();
    primaryStage.setMaxHeight(640);
    primaryStage.setMinHeight(640);
    primaryStage.setMinWidth(970);
    primaryStage.setMaxWidth(970);
    primaryStage.setResizable(true);
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(event -> {
      new CloseAppCommand().commandControl();
      event.consume();
    });
    primaryStage.setResizable(false);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
