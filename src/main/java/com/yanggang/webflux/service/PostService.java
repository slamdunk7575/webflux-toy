package com.yanggang.webflux.service;

import com.yanggang.webflux.client.PostClient;
import com.yanggang.webflux.dto.PostResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class PostService {

    private final PostClient postClient;

    public PostService(PostClient postClient) {
        this.postClient = postClient;
    }

    public Mono<PostResponseDto> getPostContent(Long id) {
        return postClient.getPost(id)
                .onErrorResume(error -> Mono.just(PostResponseDto.builder()
                        .id(id)
                        .content("Fallback data %d".formatted(id))
                        .build()));
    }

    public Flux<PostResponseDto> getMultiplePostContents(List<Long> ids) {
        return Flux.fromIterable(ids)
                .flatMap(this::getPostContent)
                .log();
    }

    public Flux<PostResponseDto> getParallelMultiplePostContents(List<Long> ids) {
        return Flux.fromIterable(ids)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(this::getPostContent)
                .log()
                .sequential();
    }
}
