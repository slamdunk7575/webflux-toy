package com.yanggang.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleRestController {

    @GetMapping("/hello-annotation")
    public Mono<String> getHello() {

        // Reactor 라면 Publisher <---> Subscriber 사이의 데이터 교환을 통해
        // Event-Driven 하게 데이터 스트림을 처리
        // 여기서 Mono 를 리턴한다는 것은 Publisher 는 있지만 Subscriber 는 없는 상황
        // Subscriber 가 Subscribe(구독) 을 하지 않으면 응답하지 않는 구조인데
        // 어떻게 가능한가?
        // 스프링 WebFlux 가 리턴되는 값에 대해서 별도의 Subscribe(구독) 을 하고 있기 때문
        // 복잡한 비지니스 로직이 더 추가된다면 Subscriber 에게 잘전달될 수 있도록 처리해줘야함
        return Mono.just("Hello Annotation Endpoint!!!");
    }

}
