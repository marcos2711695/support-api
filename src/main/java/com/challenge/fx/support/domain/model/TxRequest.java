package com.challenge.fx.support.domain.model;

public record TxRequest(
        Long userId,
        String userName,
        String fromCurrency,
        String toCurrency,
        double amount,
        double rate,
        double convertedAmount
) {}

