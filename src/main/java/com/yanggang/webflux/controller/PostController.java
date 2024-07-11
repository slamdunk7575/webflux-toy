package com.yanggang.webflux.controller;

import com.yanggang.webflux.dto.PostResponseDto;
import com.yanggang.webflux.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/{id}")
    public Mono<ResponseEntity<PostResponseDto>> getPostContent(@PathVariable Long id) {
        return postService.getPostContent(id).map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.internalServerError().build()));
    }

}
