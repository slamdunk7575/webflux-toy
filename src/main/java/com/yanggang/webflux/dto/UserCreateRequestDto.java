package com.yanggang.webflux.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {

    private String name;
    private String email;

    @Builder
    public UserCreateRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
