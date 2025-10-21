package com.challenge.fx.support.service;

import com.challenge.fx.support.domain.model.ExchangeTransaction;
import com.challenge.fx.support.domain.ports.TxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TxService {
    private final TxRepository repo;

    public Flux<ExchangeTransaction> findAll() {
        return repo.findAll();
    }

    public Mono<ExchangeTransaction> log(
            Long userId,
            String userName,
            String fromCurrency,
            String toCurrency,
            double amount,
            double rate,
            double convertedAmount
    ) {
        var tx = new ExchangeTransaction(
                null,
                userId,
                userName,
                fromCurrency,
                toCurrency,
                amount,
                rate,
                convertedAmount,
                LocalDateTime.now()
        );
        return repo.save(tx);
    }
}
