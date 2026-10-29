package com.yurtar.subman.sidebar;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SideBarView extends BorderPane {
    
    private static final Button home = new Button("Home");
    private static final Button subs = new Button("Subscriptions");
    private static final Button settings = new Button("Settings");
    private static final Button logout = new Button("Logout");

    public SideBarView() {
        getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        getStyleClass().add("sidebar");

        VBox centerContent = new VBox(8,
            new Label("SideBar"),
            home, subs, settings
        );
        setCenter(centerContent);
        setBottom(logout);
    }

}
