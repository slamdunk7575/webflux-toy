package com.yanggang.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouteConfig {

    private final SampleHandler sampleHandler;

    public RouteConfig(SampleHandler sampleHandler) {
        this.sampleHandler = sampleHandler;
    }

    /*
        Spring WebFlux 는 Controller 에서 두가지 등록 방식을 제공
        - Functional EndPoint
        - Annotation EndPoint
     */
    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .GET("/hello-functional", sampleHandler::getString)
                .build();
    }
}
