package com.yurtar.subman.common.components;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MainCard extends VBox {

  private Label month = new Label(LocalDate.now().getMonth().toString());
  private Label monthlySpent = new Label("$--");
  private Label activeSubs = new Label("0 active subs");
  private Label annualExpenses = new Label("$-- / year");

  public MainCard() {
    super(10);

    getStylesheets().add(getClass().getResource("/main-card.css").toExternalForm());
    getStyleClass().add("main-card");

    Label m = new Label("Monthly Spent");
    m.getStyleClass().add("gray-text");

    month.getStyleClass().add("month-label");
    monthlySpent.getStyleClass().add("monthly-spent");
    activeSubs.getStyleClass().add("gray-text");
    annualExpenses.getStyleClass().add("gray-text");

    getChildren().addAll(
            spaceBetween(m, month),
            monthlySpent,
            spaceBetween(activeSubs, annualExpenses)
    );
  }

  private static HBox spaceBetween(Node left, Node right) {
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    HBox row = new HBox(left, spacer, right);
    row.setAlignment(Pos.CENTER_LEFT);
    return row;
  }

  public void setAnnualExpenses(BigDecimal annualExpenses) {
    this.annualExpenses.setText(
            String.format("$%s / year", annualExpenses)
    );
  }
  public void setActiveSubs(int activeSubs) {
    this.activeSubs.setText(
            String.format("%d active subscriptions", activeSubs)
    );
  }
  public void setMonthlySpent(BigDecimal monthlySpent) {
    this.monthlySpent.setText(
            String.format("$%s", monthlySpent)
    );
  }
  public void setMonth(String month) {
    this.month.setText(month);
  }
}
