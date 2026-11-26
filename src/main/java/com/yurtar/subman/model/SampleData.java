package com.yurtar.subman.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// Fake subscriptions for the UI until we load them from the database
public class SampleData {

  public static List<Subscription> subscriptions() {
    LocalDate today = LocalDate.now();
    return List.of(
            new Subscription("Netflix", new BigDecimal("15.49"), "Monthly", today.plusDays(1), "#e50914", false),
            new Subscription("Spotify", new BigDecimal("10.99"), "Monthly", today.plusDays(3), "#1db954", true),
            new Subscription("iCloud+", new BigDecimal("2.99"), "Monthly", today.plusDays(8), "#3693f3", true),
            new Subscription("ChatGPT Plus", new BigDecimal("20.00"), "Monthly", today.plusDays(11), "#10a37f", true),
            new Subscription("Disney+", new BigDecimal("13.99"), "Monthly", today.plusDays(15), "#113ccf", false),
            new Subscription("YouTube Premium", new BigDecimal("13.99"), "Monthly", today.plusDays(18), "#ff0000", false),
            new Subscription("Duolingo Super", new BigDecimal("6.99"), "Monthly", today.plusDays(22), "#58cc02", false),
            new Subscription("Strava", new BigDecimal("11.99"), "Monthly", today.plusDays(28), "#fc4c02", false)
    );
  }
}
