module com.yurtar.subman {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;


  opens com.yurtar.subman to javafx.fxml;
  exports com.yurtar.subman;
}