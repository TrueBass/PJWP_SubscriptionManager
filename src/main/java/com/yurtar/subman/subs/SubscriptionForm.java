package com.yurtar.subman.subs;

import com.yurtar.subman.model.Subscription;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.time.LocalDate;

// Fields for one subscription. Used by the details panel (edit) and the add dialog.
public class SubscriptionForm extends VBox {

  private final TextField name = new TextField();
  private final ToggleButton monthly = new ToggleButton("Monthly");
  private final ToggleButton yearly = new ToggleButton("Yearly");
  private final TextField price = new TextField();
  private final DatePicker nextPayment = new DatePicker();
  private final ColorPicker color = new ColorPicker();
  private final CheckBox pinned = new CheckBox("Pin on home page");
  private final Label error = new Label();

  public SubscriptionForm() {
    super(8);
    getStylesheets().add(getClass().getResource("/form.css").toExternalForm());
    getStyleClass().add("sub-form");

    name.setPromptText("e.g. Netflix");
    price.setPromptText("0.00");

    ToggleGroup cycle = new ToggleGroup();
    monthly.setToggleGroup(cycle);
    yearly.setToggleGroup(cycle);
    monthly.setMaxWidth(Double.MAX_VALUE);
    yearly.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(monthly, Priority.ALWAYS);
    HBox.setHgrow(yearly, Priority.ALWAYS);
    HBox cycleToggle = new HBox(4, monthly, yearly);
    cycleToggle.getStyleClass().add("cycle-toggle");

    error.getStyleClass().add("form-error");
    error.setManaged(false);

    getChildren().addAll(
            section("PLAN DETAILS"),
            row("Name", name),
            cycleToggle,
            section("PRICE"),
            row("Amount ($)", price),
            section("PAYMENT INFO"),
            row("Next payment", nextPayment),
            section("OTHER"),
            row("Color", color),
            row(null, pinned),
            error
    );
    setSubscription(null);
  }

  // fills the fields, null = empty form for a new sub
  public void setSubscription(Subscription sub) {
    showError(null);
    if (sub == null) {
      name.clear();
      price.clear();
      monthly.setSelected(true);
      nextPayment.setValue(LocalDate.now().plusMonths(1));
      color.setValue(Color.web("#abd1c6"));
      pinned.setSelected(false);
      return;
    }
    name.setText(sub.name());
    price.setText(sub.price().toPlainString());
    yearly.setSelected(sub.cycle().equalsIgnoreCase("yearly"));
    monthly.setSelected(!yearly.isSelected());
    nextPayment.setValue(sub.nextPayment());
    color.setValue(Color.web(sub.color()));
    pinned.setSelected(sub.pinned());
  }

  // builds a sub from the fields, or returns null and shows an error if something's wrong
  public Subscription getSubscription() {
    String n = name.getText().trim();
    if (n.isEmpty()) {
      showError("Name can't be empty");
      return null;
    }
    BigDecimal p;
    try {
      p = new BigDecimal(price.getText().trim().replace(',', '.'));
    } catch (NumberFormatException e) {
      showError("Price must be a number, like 9.99");
      return null;
    }
    if (p.signum() < 0) {
      showError("Price can't be negative");
      return null;
    }
    if (nextPayment.getValue() == null) {
      showError("Pick the next payment date");
      return null;
    }
    showError(null);
    return new Subscription(n, p, yearly.isSelected() ? "Yearly" : "Monthly",
            nextPayment.getValue(), toHex(color.getValue()), pinned.isSelected());
  }

  private void showError(String text) {
    error.setText(text == null ? "" : text);
    error.setVisible(text != null);
    error.setManaged(text != null);
  }

  private static Label section(String text) {
    Label l = new Label(text);
    l.getStyleClass().add("form-section");
    return l;
  }

  // dark rounded row: label on the left, input on the right
  private static HBox row(String label, Node input) {
    HBox row = new HBox(8);
    row.getStyleClass().add("form-row");
    row.setAlignment(Pos.CENTER_LEFT);
    if (label != null) {
      Label l = new Label(label);
      l.getStyleClass().add("form-row-label");
      l.setMinWidth(Region.USE_PREF_SIZE); // don't cut the label to "Next pay..."
      Region spacer = new Region();
      HBox.setHgrow(spacer, Priority.ALWAYS);
      row.getChildren().addAll(l, spacer);
    }
    row.getChildren().add(input);
    return row;
  }

  private static String toHex(Color c) {
    return String.format("#%02x%02x%02x",
            Math.round(c.getRed() * 255), Math.round(c.getGreen() * 255), Math.round(c.getBlue() * 255));
  }
}
