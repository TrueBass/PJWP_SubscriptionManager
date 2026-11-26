package com.yurtar.subman.subs;

import com.yurtar.subman.common.components.SubscriptionList;
import com.yurtar.subman.model.SampleData;
import com.yurtar.subman.model.Subscription;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

// All subscriptions page. Left: header + list, right side is for the details panel (later).
public class SubscriptionsView extends HBox {

  public SubscriptionsView() {
    super(20);
    getStylesheets().add(getClass().getResource("/subs.css").toExternalForm());
    getStyleClass().add("subs-page");

    List<Subscription> subs = SampleData.subscriptions();

    Label title = new Label("Subscriptions");
    title.getStyleClass().add("subs-title");
    Label summary = new Label(subs.size() + " active · $" + monthlyTotal(subs) + " / month");
    summary.getStyleClass().add("subs-summary");

    SubscriptionList list = new SubscriptionList();
    list.setSubscriptions(subs);
    VBox.setVgrow(list, Priority.ALWAYS);

    VBox left = new VBox(4, title, summary, list);
    left.getStyleClass().add("subs-left");
    HBox.setHgrow(left, Priority.ALWAYS);

    getChildren().add(left);
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
