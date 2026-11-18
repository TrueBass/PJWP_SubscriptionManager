package com.yurtar.subman.common.components;

import com.yurtar.subman.model.Subscription;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

// Row with icon, name + payment info, and price per cycle.
public class SubscriptionCard extends HBox {

  private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH);

  public SubscriptionCard(Subscription sub) {
    super(12);
    getStyleClass().add("sub-card");
    setAlignment(Pos.CENTER_LEFT);

    long days = ChronoUnit.DAYS.between(LocalDate.now(), sub.nextPayment());
    if (days <= 1) {
      getStyleClass().add("sub-card-soon");
    }

    VBox info = new VBox(2,
            label(sub.name(), "sub-name"),
            label(sub.cycle() + " · " + dueText(days, sub.nextPayment()), "sub-meta"));

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    VBox price = new VBox(2,
            label("$" + sub.price(), "sub-price"),
            label(sub.cycle().equalsIgnoreCase("yearly") ? "/ year" : "/ month", "sub-meta"));
    price.setAlignment(Pos.CENTER_RIGHT);

    getChildren().addAll(icon(sub), info, spacer, price);
  }

  private static StackPane icon(Subscription sub) {
    StackPane icon = new StackPane(label(sub.name().substring(0, 1), "sub-icon-letter"));
    icon.getStyleClass().add("sub-icon");
    icon.setStyle("-fx-background-color: " + sub.color() + ";");
    return icon;
  }

  private static Label label(String text, String styleClass) {
    Label l = new Label(text);
    l.getStyleClass().add(styleClass);
    return l;
  }

  private static String dueText(long days, LocalDate date) {
    if (days <= 0) return "Due today";
    if (days == 1) return "Due tomorrow";
    return "Next " + date.format(DATE);
  }
}
