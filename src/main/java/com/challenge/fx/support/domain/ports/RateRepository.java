package com.challenge.fx.support.domain.ports;
import com.challenge.fx.support.domain.model.ExchangeRate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RateRepository extends ReactiveCrudRepository<ExchangeRate, Long> {
    Mono<ExchangeRate> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}
