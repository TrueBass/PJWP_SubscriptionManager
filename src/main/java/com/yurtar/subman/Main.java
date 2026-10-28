package com.yurtar.subman;

import com.yurtar.subman.db.Database;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    try {
      Connection psqlConnection = Database.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
