package com.yurtar.subman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import com.yurtar.subman.sidebar.SideBarView;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws IOException {

    HBox layout = new HBox(new SideBarView());

    Scene scene = new Scene(layout, 900, 600);
    scene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());
    
    stage.setTitle("Subscriptions");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
