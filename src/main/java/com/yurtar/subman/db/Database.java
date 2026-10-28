package com.yurtar.subman.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
  private static final Properties props = new Properties();

  static {
    try (var in = Database.class.getResourceAsStream("/db.properties")) {
      props.load(in);
    } catch (IOException e) {
      throw new RuntimeException("Can't load db.properties", e);
    }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.user"),
            props.getProperty("db.password"));
  }
}
