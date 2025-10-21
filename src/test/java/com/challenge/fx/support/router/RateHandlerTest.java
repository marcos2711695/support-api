package com.challenge.fx.support.router;

import com.challenge.fx.support.service.RateService;
import com.challenge.fx.support.domain.model.ExchangeRate;
import com.challenge.fx.support.domain.ports.RateRepository;
import com.challenge.fx.support.handler.RateHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateHandlerTest {

    @Mock
    RateRepository repo;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        RateService service = new RateService(repo);
        RateHandler handler = new RateHandler(service);
        Routes routes = new Routes();
        client = WebTestClient.bindToRouterFunction(routes.rateRoutes(handler)).build();
    }

    @Test
    void get_ok() {
        when(repo.findByFromCurrencyAndToCurrency("USD","PEN"))
                .thenReturn(Mono.just(new ExchangeRate(1L,"USD","PEN",3.8, Instant.now())));

        client.get().uri("/api/v1/rates?from=USD&to=PEN")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.rate").isEqualTo(3.8);
    }

    @Test
    void upsert_existing_ok() {
        when(repo.findByFromCurrencyAndToCurrency("USD","PEN"))
                .thenReturn(Mono.just(new ExchangeRate(1L,"USD","PEN",3.8, Instant.now())));
        when(repo.save(any()))
                .thenAnswer(inv -> Mono.just(
                        new ExchangeRate(1L,"USD","PEN",3.9, Instant.now())));

        String body = "{\"from\":\"USD\",\"to\":\"PEN\",\"rate\":3.9}";
        client.post().uri("/api/v1/rates")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.rate").isEqualTo(3.9);
    }
}
