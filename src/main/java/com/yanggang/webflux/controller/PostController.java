package com.yanggang.webflux.controller;

import com.yanggang.webflux.dto.PostCreateRequestDto;
import com.yanggang.webflux.dto.PostResponseDto;
import com.yanggang.webflux.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v2/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Mono<PostResponseDto> create(@RequestBody PostCreateRequestDto request) {
        return postService.create(request).map(PostResponseDto::toDto);
    }

    @GetMapping
    public Flux<PostResponseDto> findAllPosts() {
        return postService.findAll()
                .map(PostResponseDto::toDto);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PostResponseDto>> findPost(@PathVariable Long id) {
        return postService.findById(id)
                .map(post -> ResponseEntity.ok().body(PostResponseDto.toDto(post)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> deletePost(@PathVariable Long id) {
        return postService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
