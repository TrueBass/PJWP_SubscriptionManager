package com.yurtar.subman.home;

import javafx.scene.layout.VBox;

public class HomeView extends VBox {
    public HomeView() {
        super();
        getStylesheets().add(getClass().getResource("/home.css").toExternalForm());
        getStyleClass().add("root");

    }
}
