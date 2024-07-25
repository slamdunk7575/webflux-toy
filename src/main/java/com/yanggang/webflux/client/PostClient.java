package com.yanggang.webflux.client;

import com.yanggang.webflux.dto.ExternalPostResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
public class PostClient {

    private final WebClient webClient;
    private final String url = "http://127.0.0.1:8090";

    public PostClient(WebClient webClient) {
        this.webClient = webClient;
    }

    // 외부 서버 요청
    public Mono<ExternalPostResponseDto> getPost(Long id) {
        String uriString = UriComponentsBuilder.fromHttpUrl(url)
                .path("/posts/%d".formatted(id))
                .buildAndExpand()
                .toUriString();

        return webClient.get()
                .uri(uriString)
                .retrieve()
                .bodyToMono(ExternalPostResponseDto.class);
    }


}
