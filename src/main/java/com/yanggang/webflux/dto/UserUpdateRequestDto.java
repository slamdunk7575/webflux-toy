package com.yanggang.webflux.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String name;
    private String email;

    @Builder
    public UserUpdateRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
