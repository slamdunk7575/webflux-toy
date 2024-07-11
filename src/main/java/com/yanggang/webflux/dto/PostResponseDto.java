package com.yanggang.webflux.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String content;

    @Builder
    public PostResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
