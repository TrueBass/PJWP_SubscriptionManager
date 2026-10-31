package com.yurtar.subman.home;

import com.yurtar.subman.common.components.MainCard;
import javafx.scene.layout.VBox;

public class HomeView extends VBox {
    public HomeView() {
        super();
        getStylesheets().add(getClass().getResource("/home.css").toExternalForm());
        getStyleClass().add("root");

        /* TODO:
            * upcoming payments card
            * pinned subs card (same as subs card for all subs View)
            1. upcoming payments list
            2. pinned subs list, that can be managed

            list could be generalized and taken out in a component
            or (1) & (2) will have their own list.
        */

        getChildren().add(new MainCard());
    }
}
