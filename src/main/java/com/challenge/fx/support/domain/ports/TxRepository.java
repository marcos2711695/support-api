package com.challenge.fx.support.domain.ports;


import com.challenge.fx.support.domain.model.ExchangeTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TxRepository {
    Mono<ExchangeTransaction> save(ExchangeTransaction tx);
    Flux<ExchangeTransaction> findAll();
}

