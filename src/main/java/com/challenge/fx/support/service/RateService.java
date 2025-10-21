package com.challenge.fx.support.service;

import com.challenge.fx.support.domain.model.ExchangeRate;
import com.challenge.fx.support.domain.ports.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RateService {
    private final RateRepository repo;

    public Mono<ExchangeRate> get(String from, String to) {
        return repo.findByFromCurrencyAndToCurrency(from, to);
    }

    public Mono<ExchangeRate> upsert(String from, String to, double rate) {
        return repo.findByFromCurrencyAndToCurrency(from, to)
                .flatMap(existing -> repo.save(new ExchangeRate(existing.id(), from, to, rate, Instant.now())))
                .switchIfEmpty(repo.save(new ExchangeRate(null, from, to, rate, Instant.now())));
    }
}
