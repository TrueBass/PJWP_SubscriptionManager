package com.yurtar.subman;

import com.yurtar.subman.home.HomeView;
import com.yurtar.subman.sidebar.Page;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import com.yurtar.subman.sidebar.SideBarView;

public class Main extends Application {

  private final Map<Page, Node> pages = new EnumMap<>(Page.class);
  private final HBox layout = new HBox();

  private final SideBarView sideBar;

  public Main() {
    sideBar = new SideBarView(this::navigate);
  }

  @Override
  public void start(Stage stage) throws IOException {
    pages.put(Page.HOME, new HomeView());
    pages.put(Page.SETTINGS, new Label("SETTINGS"));
    pages.put(Page.SUBSCRIPTIONS, new Label("SUBS"));

    pages.values().forEach(page -> HBox.setHgrow(page, Priority.ALWAYS));

    layout.getChildren().addAll(sideBar, pages.get(Page.HOME));
    navigate(Page.HOME);

    Scene scene = new Scene(layout, 900, 600);
    scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    
    stage.setTitle("Sub Man");
    stage.setScene(scene);
    stage.show();
  }

  private void navigate(Page page) {
    layout.getChildren().set(1, pages.get(page));
    sideBar.setActive(page);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
