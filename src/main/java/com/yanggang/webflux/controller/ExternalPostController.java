package com.yanggang.webflux.controller;

import com.yanggang.webflux.dto.ExternalPostResponseDto;
import com.yanggang.webflux.service.ExternalPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ExternalPostController {

    private final ExternalPostService externalPostService;

    public ExternalPostController(ExternalPostService externalPostService) {
        this.externalPostService = externalPostService;
    }

    @GetMapping("/posts/{id}")
    public Mono<ResponseEntity<ExternalPostResponseDto>> getPostContent(@PathVariable Long id) {
        return externalPostService.getPostContent(id).map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.internalServerError().build()));
    }

    @GetMapping("/posts/search")
    public Flux<ExternalPostResponseDto> getMultiplePostContents(
            @RequestParam(name = "ids") List<Long> ids
    ) {
        // return postService.getMultiplePostContents(ids);
        return externalPostService.getParallelMultiplePostContents(ids);
    }
}
