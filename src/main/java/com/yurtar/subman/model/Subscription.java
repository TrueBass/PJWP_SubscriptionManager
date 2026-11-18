package com.yurtar.subman.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Subscription(
        String name,
        BigDecimal price,
        String cycle,
        LocalDate nextPayment,
        String color,
        boolean pinned
) {}
