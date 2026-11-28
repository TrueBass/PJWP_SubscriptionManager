package com.yurtar.subman.home;

import com.yurtar.subman.common.components.MainCard;
import com.yurtar.subman.common.components.SubscriptionList;
import com.yurtar.subman.model.SampleData;
import com.yurtar.subman.model.Subscription;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.List;

public class HomeView extends HBox {
    public HomeView() {
        super(20);
        getStylesheets().add(getClass().getResource("/home.css").toExternalForm());
        getStyleClass().add("root");

        List<Subscription> subs = SampleData.subscriptions();

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
