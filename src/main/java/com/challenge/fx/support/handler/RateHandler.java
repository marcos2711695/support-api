package com.challenge.fx.support.handler;
import com.challenge.fx.support.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RateHandler {
    private final RateService service;

    public Mono<ServerResponse> get(ServerRequest req) {
        String from = req.queryParam("from").orElseThrow();
        String to = req.queryParam("to").orElseThrow();
        return service.get(from, to)
                .flatMap(rate -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(new RateResponse(rate.fromCurrency(), rate.toCurrency(), rate.rate())))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> upsert(ServerRequest req) {
        return req.bodyToMono(RateRequest.class)
                .flatMap(r -> service.upsert(r.from(), r.to(), r.rate()))
                .flatMap(rate -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(new RateResponse(rate.fromCurrency(), rate.toCurrency(), rate.rate())));
    }

    record RateRequest(String from, String to, double rate) {}
    record RateResponse(String from, String to, double rate) {}
}
