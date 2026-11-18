package com.yurtar.subman.home;

import com.yurtar.subman.common.components.MainCard;
import com.yurtar.subman.common.components.SubscriptionList;
import com.yurtar.subman.model.Subscription;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class HomeView extends HBox {
    public HomeView() {
        super(20);
        getStylesheets().add(getClass().getResource("/home.css").toExternalForm());
        getStyleClass().add("root");

        // Sample data until subscriptions come from the database
        LocalDate today = LocalDate.now();
        List<Subscription> subs = List.of(
                new Subscription("Netflix", new BigDecimal("15.49"), "Monthly", today.plusDays(1), "#e50914", false),
                new Subscription("Spotify", new BigDecimal("10.99"), "Monthly", today.plusDays(3), "#1db954", true),
                new Subscription("iCloud+", new BigDecimal("2.99"), "Monthly", today.plusDays(8), "#3693f3", true),
                new Subscription("ChatGPT Plus", new BigDecimal("20.00"), "Monthly", today.plusDays(11), "#10a37f", true),
                new Subscription("Disney+", new BigDecimal("13.99"), "Monthly", today.plusDays(15), "#113ccf", false),
                new Subscription("YouTube Premium", new BigDecimal("13.99"), "Monthly", today.plusDays(18), "#ff0000", false)
        );

        SubscriptionList upcoming = new SubscriptionList("Upcoming payments", "View All");
        upcoming.setSubscriptions(subs.stream()
                .sorted(Comparator.comparing(Subscription::nextPayment))
                .toList());

        SubscriptionList pinned = new SubscriptionList("Pinned", "Manage");
        pinned.setSubscriptions(subs.stream().filter(Subscription::pinned).toList());
        VBox.setVgrow(pinned, Priority.ALWAYS);

        // Left: spending card with pinned list below. Right: upcoming payments.
        VBox left = new VBox(20, new MainCard(), pinned);
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(upcoming, Priority.ALWAYS);

        getChildren().addAll(left, upcoming);
    }
}
