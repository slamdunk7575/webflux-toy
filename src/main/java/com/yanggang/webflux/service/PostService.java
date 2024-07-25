package com.yanggang.webflux.service;

import com.yanggang.webflux.dto.PostCreateRequestDto;
import com.yanggang.webflux.repository.Post;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface PostService {

    Mono<Post> create(PostCreateRequestDto requestDto);

    Flux<Post> findAll();

    Mono<Post> findById(Long id);

    Mono<Void> deleteById(Long id);
}
