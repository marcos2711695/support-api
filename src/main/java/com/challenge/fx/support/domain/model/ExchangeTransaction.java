package com.challenge.fx.support.domain.model;

import java.time.LocalDateTime;


public record ExchangeTransaction(
        Long id,
        Long userId,
        String userName,
        String fromCurrency,
        String toCurrency,
        double amount,
        double rate,
        double convertedAmount,
        LocalDateTime createdAt
) {}

