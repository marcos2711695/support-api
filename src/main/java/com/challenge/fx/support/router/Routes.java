package com.challenge.fx.support.router;
import com.challenge.fx.support.handler.RateHandler;
import com.challenge.fx.support.handler.TxHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class Routes {

    @Bean
    RouterFunction<ServerResponse> rateRoutes(RateHandler handler) {
        return RouterFunctions
                .route(GET("/api/v1/rates"), handler::get)
                .andRoute(POST("/api/v1/rates"), handler::upsert);
    }

    @Bean
    RouterFunction<ServerResponse> txRoutes(TxHandler handler) {
        return RouterFunctions
                .route(POST("/api/v1/transactions"), handler::create)
                .andRoute(GET("/api/v1/transactions"), handler::findAll);
    }

}
