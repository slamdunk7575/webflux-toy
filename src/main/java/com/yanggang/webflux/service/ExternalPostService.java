package com.yanggang.webflux.service;

import com.yanggang.webflux.client.PostClient;
import com.yanggang.webflux.dto.ExternalPostResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class ExternalPostService {

    private final PostClient postClient;

    public ExternalPostService(PostClient postClient) {
        this.postClient = postClient;
    }

    public Mono<ExternalPostResponseDto> getPostContent(Long id) {
        return postClient.getPost(id)
                .onErrorResume(error -> Mono.just(ExternalPostResponseDto.builder()
                        .id(id)
                        .content("Fallback data %d".formatted(id))
                        .build()));
    }

    public Flux<ExternalPostResponseDto> getMultiplePostContents(List<Long> ids) {
        return Flux.fromIterable(ids)
                .flatMap(this::getPostContent)
                .log();
    }

    public Flux<ExternalPostResponseDto> getParallelMultiplePostContents(List<Long> ids) {
        return Flux.fromIterable(ids)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(this::getPostContent)
                .log()
                .sequential();
    }
}
