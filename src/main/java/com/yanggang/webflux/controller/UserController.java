package com.yanggang.webflux.controller;

import com.yanggang.webflux.dto.UserCreateRequestDto;
import com.yanggang.webflux.dto.UserResponseDto;
import com.yanggang.webflux.dto.UserUpdateRequestDto;
import com.yanggang.webflux.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Mono<UserResponseDto> createUser(@RequestBody UserCreateRequestDto requestDto) {
        return userService.create(requestDto.getName(), requestDto.getEmail())
                .map(UserResponseDto::toDto);
    }

    @GetMapping
    public Flux<UserResponseDto> findAllUsers() {
        return userService.findAll()
                .map((UserResponseDto::toDto));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponseDto>> findUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(UserResponseDto.toDto(user)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> deleteUser(@PathVariable Long id) {
        return userService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponseDto>> updateUser(@PathVariable Long id,
                                              @RequestBody UserUpdateRequestDto requestDto) {
        return userService.update(id, requestDto.getName(), requestDto.getEmail())
                .map(user -> ResponseEntity.ok(UserResponseDto.toDto(user)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
