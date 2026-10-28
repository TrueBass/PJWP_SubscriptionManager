module com.yurtar.subman {
  requires javafx.controls;
  requires javafx.fxml;


  opens com.yurtar.subman to javafx.fxml;
  exports com.yurtar.subman;
}