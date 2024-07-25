package com.yanggang.webflux.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequestDto {

    private Long userId;
    private String title;
    private String content;

}
