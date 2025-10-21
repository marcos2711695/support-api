package com.challenge.fx.support.handler;
import com.challenge.fx.support.domain.model.TxRequest;
import com.challenge.fx.support.service.TxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TxHandler {
    private final TxService service;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(TxRequest.class)
                .doOnNext(tx -> System.out.println("📦 Recibido en support: " + tx))
                .flatMap(tx -> service.log(
                        tx.userId(),
                        tx.userName(),
                        tx.fromCurrency(),
                        tx.toCurrency(),
                        tx.amount(),
                        tx.rate(),
                        tx.convertedAmount()
                ))
                .flatMap(savedTx -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedTx)
                );
    }

    public Mono<ServerResponse> findAll(ServerRequest req) {
        return service.findAll()
                .collectList()
                .flatMap(list -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(list));
    }

    record TxResponse(Long id, String status) {}
}
