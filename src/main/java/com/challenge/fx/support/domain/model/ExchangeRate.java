package com.challenge.fx.support.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("exchange_rate")
public record ExchangeRate(@Id Long id, String fromCurrency, String toCurrency, double rate, Instant updatedAt) {}
