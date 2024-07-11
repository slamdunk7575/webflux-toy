package com.yanggang.webflux.service;

import com.yanggang.webflux.client.PostClient;
import com.yanggang.webflux.dto.PostResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PostService {

    private final PostClient postClient;

    public PostService(PostClient postClient) {
        this.postClient = postClient;
    }

    public Mono<PostResponseDto> getPostContent(Long id) {
        return postClient.getPost(id);
    }

}
