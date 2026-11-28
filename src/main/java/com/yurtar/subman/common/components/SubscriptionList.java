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
import java.util.function.Consumer;

// Titled, scrollable list of subscription cards with an action button in the header.
public class SubscriptionList extends VBox {

  private final VBox items = new VBox(8);
  private Button action;
  private Consumer<Subscription> onSelect;
  private Subscription selected;

  // List without the title row (the page has its own header)
  public SubscriptionList() {
    this(null, null);
  }

  public SubscriptionList(String title, String actionText) {
    super(10);
    getStylesheets().add(getClass().getResource("/subscription.css").toExternalForm());
    getStyleClass().add("sub-list");

    if (title != null) {
      Label titleLabel = new Label(title);
      titleLabel.getStyleClass().add("sub-list-title");

      Region spacer = new Region();
      HBox.setHgrow(spacer, Priority.ALWAYS);
      HBox header = new HBox(titleLabel, spacer);
      header.setAlignment(Pos.CENTER_LEFT);

      if (actionText != null) {
        action = new Button(actionText);
        action.getStyleClass().add("sub-list-action");
        header.getChildren().add(action);
      }
      getChildren().add(header);
    }

    items.getStyleClass().add("sub-items");

    ScrollPane scroll = new ScrollPane(items);
    scroll.getStyleClass().add("sub-scroll");
    scroll.setFitToWidth(true);
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    VBox.setVgrow(scroll, Priority.ALWAYS);

    getChildren().add(scroll);
  }

  public void setSubscriptions(List<Subscription> subs) {
    items.getChildren().setAll(subs.stream().map(this::makeCard).toList());
  }

  // makes cards clickable, the clicked one gets highlighted
  public void setOnSelect(Consumer<Subscription> onSelect) {
    this.onSelect = onSelect;
  }

  public void setSelected(Subscription sub) {
    selected = sub;
    for (var node : items.getChildren()) {
      SubscriptionCard card = (SubscriptionCard) node;
      card.getStyleClass().remove("sub-card-selected");
      if (card.getSubscription().equals(sub)) {
        card.getStyleClass().add("sub-card-selected");
      }
    }
  }

  private SubscriptionCard makeCard(Subscription sub) {
    SubscriptionCard card = new SubscriptionCard(sub);
    if (onSelect != null) {
      card.getStyleClass().add("sub-card-clickable");
      if (sub.equals(selected)) {
        card.getStyleClass().add("sub-card-selected");
      }
      card.setOnMouseClicked(e -> {
        setSelected(sub);
        onSelect.accept(sub);
      });
    }
    return card;
  }

  public Button getAction() {
    return action;
  }
}
