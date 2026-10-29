module com.yurtar.subman {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires org.kordamp.ikonli.materialdesign2;
  requires org.kordamp.ikonli.javafx;


  opens com.yurtar.subman to javafx.fxml;
  exports com.yurtar.subman;
}