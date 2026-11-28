package com.yurtar.subman.subs;

import com.yurtar.subman.common.components.SubscriptionList;
import com.yurtar.subman.model.SampleData;
import com.yurtar.subman.model.Subscription;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

// All subscriptions page. Left: header + search + list, right side is for the details panel (later).
public class SubscriptionsView extends HBox {

  private final List<Subscription> subs = new ArrayList<>(SampleData.subscriptions());

  private final Label summary = new Label();
  private final TextField search = new TextField();
  private final SubscriptionList list = new SubscriptionList();
  private final Label empty = new Label("No subscriptions found");

  public SubscriptionsView() {
    super(20);
    getStylesheets().add(getClass().getResource("/subs.css").toExternalForm());
    getStyleClass().add("subs-page");

    Label title = new Label("Subscriptions");
    title.getStyleClass().add("subs-title");
    summary.getStyleClass().add("subs-summary");

    search.setPromptText("Search subscriptions...");
    search.getStyleClass().add("subs-search");
    search.textProperty().addListener((obs, oldText, newText) -> refresh());

    empty.getStyleClass().add("subs-empty");
    VBox.setVgrow(list, Priority.ALWAYS);
    VBox.setMargin(search, new Insets(0, 0, 8, 0));

    VBox left = new VBox(4, title, summary, search, empty, list);
    left.getStyleClass().add("subs-left");
    HBox.setHgrow(left, Priority.ALWAYS);

    getChildren().add(left);
    refresh();
  }

  // rebuilds the list from subs + current search text
  private void refresh() {
    summary.setText(subs.size() + " active · $" + monthlyTotal(subs) + " / month");

    String query = search.getText().trim().toLowerCase();
    List<Subscription> shown = subs.stream()
            .filter(s -> s.name().toLowerCase().contains(query))
            .toList();

    list.setSubscriptions(shown);
    empty.setVisible(shown.isEmpty());
    empty.setManaged(shown.isEmpty());
  }

  // yearly subs count as price / 12
  private static BigDecimal monthlyTotal(List<Subscription> subs) {
    BigDecimal total = BigDecimal.ZERO;
    for (Subscription s : subs) {
      if (s.cycle().equalsIgnoreCase("yearly")) {
        total = total.add(s.price().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP));
      } else {
        total = total.add(s.price());
      }
    }
    return total;
  }
}
