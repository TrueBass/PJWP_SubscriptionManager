package com.yurtar.subman.common.components;

import com.yurtar.subman.model.Subscription;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

// Titled, scrollable list of subscription cards with an action button in the header.
public class SubscriptionList extends VBox {

  private final VBox items = new VBox(8);
  private final Button action;

  public SubscriptionList(String title, String actionText) {
    super(10);
    getStylesheets().add(getClass().getResource("/subscription.css").toExternalForm());
    getStyleClass().add("sub-list");

    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("sub-list-title");
    action = new Button(actionText);
    action.getStyleClass().add("sub-list-action");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    HBox header = new HBox(titleLabel, spacer, action);
    header.setAlignment(Pos.CENTER_LEFT);

    items.getStyleClass().add("sub-items");

    ScrollPane scroll = new ScrollPane(items);
    scroll.getStyleClass().add("sub-scroll");
    scroll.setFitToWidth(true);
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    VBox.setVgrow(scroll, Priority.ALWAYS);

    getChildren().addAll(header, scroll);
  }

  public void setSubscriptions(List<Subscription> subs) {
    items.getChildren().setAll(subs.stream().map(SubscriptionCard::new).toList());
  }

  public Button getAction() {
    return action;
  }
}
