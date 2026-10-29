package com.yurtar.subman.sidebar;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class SideBarView extends BorderPane {


    private final Map<Page, Button> buttons = new EnumMap<>(Page.class);
    private final Button logout = new Button("Logout");

    public SideBarView(Consumer<Page> onNavigate) {
        getStylesheets().add(getClass().getResource("/sidebar.css").toExternalForm());
        getStyleClass().add("sidebar");

        for(Page page: Page.values()) {
            Button btn = new Button(page.getTitle());
            btn.setOnAction(event -> onNavigate.accept(page));
            buttons.put(page, btn);
        }

        VBox centerContent = new VBox(8);
        centerContent.getChildren().addAll(buttons.values());
        setCenter(centerContent);
        setBottom(logout);
    }

    public void setActive(Page page) {
        buttons.values().forEach(b -> b.getStyleClass().remove("active-btn"));
        buttons.get(page).getStyleClass().add("active-btn");
    }
}
