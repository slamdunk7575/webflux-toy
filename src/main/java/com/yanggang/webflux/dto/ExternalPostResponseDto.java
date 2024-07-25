package com.yanggang.webflux.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExternalPostResponseDto {
    private Long id;
    private String content;

    @Builder
    public ExternalPostResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
