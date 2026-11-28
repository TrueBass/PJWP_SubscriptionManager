package com.yurtar.subman.subs;

import com.yurtar.subman.common.components.SubscriptionList;
import com.yurtar.subman.model.SampleData;
import com.yurtar.subman.model.Subscription;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// All subscriptions page. Left: header + search/filters + list, right: details of the clicked sub.
public class SubscriptionsView extends HBox {

  private static final String ANY_DATE = "Any date";
  private static final String NEXT_7 = "Next 7 days";
  private static final String NEXT_30 = "Next 30 days";

  private static final String ANY_PRICE = "Any price";
  private static final String UNDER_10 = "Under $10";
  private static final String FROM_10_TO_15 = "$10 - $15";
  private static final String OVER_15 = "Over $15";

  private final List<Subscription> subs = new ArrayList<>(SampleData.subscriptions());

  private final Label summary = new Label();
  private final TextField search = new TextField();
  private final ComboBox<String> dateFilter = new ComboBox<>();
  private final ComboBox<String> priceFilter = new ComboBox<>();
  private final SubscriptionList list = new SubscriptionList();
  private final Label empty = new Label("No subscriptions found");
  private final Button addButton = new Button();
  private final VBox details = new VBox(12);

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

    dateFilter.getItems().addAll(ANY_DATE, NEXT_7, NEXT_30);
    dateFilter.setValue(ANY_DATE);
    priceFilter.getItems().addAll(ANY_PRICE, UNDER_10, FROM_10_TO_15, OVER_15);
    priceFilter.setValue(ANY_PRICE);
    for (ComboBox<String> box : List.of(dateFilter, priceFilter)) {
      box.getStyleClass().add("subs-filter");
      box.setMaxWidth(Double.MAX_VALUE);
      HBox.setHgrow(box, Priority.ALWAYS);
      box.setOnAction(e -> refresh());
    }
    HBox filters = new HBox(8, dateFilter, priceFilter);
    VBox.setMargin(filters, new Insets(0, 0, 8, 0));

    empty.getStyleClass().add("subs-empty");

    // round "+" button floating over the bottom right corner of the list
    addButton.setGraphic(new FontIcon("mdi2p-plus"));
    addButton.getStyleClass().add("add-button");
    StackPane.setAlignment(addButton, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(addButton, new Insets(0, 20, 12, 0));
    // TODO open add subscription form
    StackPane listArea = new StackPane(list, addButton);
    VBox.setVgrow(listArea, Priority.ALWAYS);

    VBox left = new VBox(8, title, summary, search, filters, empty, listArea);
    left.getStyleClass().add("subs-left");
    HBox.setHgrow(left, Priority.ALWAYS);

    list.setOnSelect(this::showDetails);
    details.getStyleClass().add("subs-details");
    HBox.setHgrow(details, Priority.ALWAYS);
    showDetails(null);

    getChildren().addAll(left, details);
    refresh();
  }

  // rebuilds the list from subs + current search text and filters
  private void refresh() {
    summary.setText(subs.size() + " active · $" + monthlyTotal(subs) + " / month");

    String query = search.getText().trim().toLowerCase();
    List<Subscription> shown = subs.stream()
            .filter(s -> s.name().toLowerCase().contains(query))
            .filter(this::matchesDate)
            .filter(this::matchesPrice)
            .toList();

    list.setSubscriptions(shown);
    empty.setVisible(shown.isEmpty());
    empty.setManaged(shown.isEmpty());
  }

  // TODO edit form for the selected sub
  private void showDetails(Subscription sub) {
    if (sub == null) {
      Label hint = new Label("Select a subscription to see its details");
      hint.getStyleClass().add("subs-details-hint");
      details.getChildren().setAll(hint);
      return;
    }
    Label name = new Label(sub.name());
    name.getStyleClass().add("subs-title");
    details.getChildren().setAll(name);
  }

  private boolean matchesDate(Subscription s) {
    LocalDate today = LocalDate.now();
    return switch (dateFilter.getValue()) {
      case NEXT_7 -> !s.nextPayment().isAfter(today.plusDays(7));
      case NEXT_30 -> !s.nextPayment().isAfter(today.plusDays(30));
      default -> true;
    };
  }

  // compares the monthly price, so a yearly sub isn't always "over $15"
  private boolean matchesPrice(Subscription s) {
    BigDecimal price = monthlyPrice(s);
    return switch (priceFilter.getValue()) {
      case UNDER_10 -> price.compareTo(BigDecimal.TEN) < 0;
      case FROM_10_TO_15 -> price.compareTo(BigDecimal.TEN) >= 0 && price.compareTo(BigDecimal.valueOf(15)) <= 0;
      case OVER_15 -> price.compareTo(BigDecimal.valueOf(15)) > 0;
      default -> true;
    };
  }

  // yearly subs count as price / 12
  private static BigDecimal monthlyPrice(Subscription s) {
    if (s.cycle().equalsIgnoreCase("yearly")) {
      return s.price().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
    }
    return s.price();
  }

  private static BigDecimal monthlyTotal(List<Subscription> subs) {
    BigDecimal total = BigDecimal.ZERO;
    for (Subscription s : subs) {
      total = total.add(monthlyPrice(s));
    }
    return total;
  }
}
