package com.challenge.fx.support.domain.ports.impl;

import com.challenge.fx.support.domain.model.ExchangeTransaction;
import com.challenge.fx.support.domain.ports.TxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TxRepositoryImpl implements TxRepository {

    private final DatabaseClient db;

    @Override
    public Mono<ExchangeTransaction> save(ExchangeTransaction tx) {
        return db.sql("""
        INSERT INTO exchange_tx (
            USER_ID, USER_NAME, FROM_CURRENCY, TO_CURRENCY,
            AMOUNT, RATE, CONVERTED_AMOUNT, CREATED_AT
        )
        VALUES (:userId, :userName, :fromCurrency, :toCurrency,
                :amount, :rate, :convertedAmount, :createdAt)
    """)
                .bind("userId", tx.userId())
                .bind("userName", tx.userName())
                .bind("fromCurrency", tx.fromCurrency())
                .bind("toCurrency", tx.toCurrency())
                .bind("amount", tx.amount())
                .bind("rate", tx.rate())
                .bind("convertedAmount", tx.convertedAmount())
                .bind("createdAt", tx.createdAt())
                .filter(statement -> statement.returnGeneratedValues("ID"))
                .fetch()
                .first()
                .map(row -> new ExchangeTransaction(
                        ((Number) row.get("ID")).longValue(),
                        tx.userId(),
                        tx.userName(),
                        tx.fromCurrency(),
                        tx.toCurrency(),
                        tx.amount(),
                        tx.rate(),
                        tx.convertedAmount(),
                        tx.createdAt()
                ));
    }




    @Override
    public Flux<ExchangeTransaction> findAll() {
        return db.sql("SELECT * FROM exchange_tx")
                .map((row, meta) -> new ExchangeTransaction(
                        row.get("ID", Long.class),
                        row.get("USER_ID", Long.class),
                        row.get("USER_NAME", String.class),
                        row.get("FROM_CURRENCY", String.class),
                        row.get("TO_CURRENCY", String.class),
                        row.get("AMOUNT", Double.class),
                        row.get("RATE", Double.class),
                        row.get("CONVERTED_AMOUNT", Double.class),
                        row.get("CREATED_AT", java.time.LocalDateTime.class)
                ))
                .all();
    }
}
