package com.yurtar.subman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws IOException {

    VBox layout = new VBox();
    Scene scene = new Scene(layout, 600, 400);
    
    stage.setTitle("Subscriptions");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
