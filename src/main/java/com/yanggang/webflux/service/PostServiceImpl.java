package com.yanggang.webflux.service;

import com.yanggang.webflux.dto.PostCreateRequestDto;
import com.yanggang.webflux.repository.Post;
import com.yanggang.webflux.repository.PostR2dbcRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostServiceImpl implements PostService {

    private final PostR2dbcRepository postR2dbcRepository;

    public PostServiceImpl(PostR2dbcRepository postR2dbcRepository) {
        this.postR2dbcRepository = postR2dbcRepository;
    }

    @Override
    public Mono<Post> create(PostCreateRequestDto requestDto) {
        return postR2dbcRepository.save(Post.builder()
                .userId(requestDto.getUserId())
                .title(requestDto.getTitle())
                .content(requestDto.getTitle())
                .build());
    }

    @Override
    public Flux<Post> findAll() {
        return postR2dbcRepository.findAll();
    }

    @Override
    public Mono<Post> findById(Long id) {
        return postR2dbcRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return postR2dbcRepository.deleteById(id);
    }
}
