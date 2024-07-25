package com.yanggang.webflux.controller;

import com.yanggang.webflux.dto.UserCreateRequestDto;
import com.yanggang.webflux.dto.UserResponseDto;
import com.yanggang.webflux.dto.UserUpdateRequestDto;
import com.yanggang.webflux.repository.User;
import com.yanggang.webflux.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebFluxTest(UserController.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    void createUser() {
        when(userService.create("slamdunk7575", "slamdunk7575@test.com"))
                .thenReturn(Mono.just(User.builder()
                        .name("slamdunk7575")
                        .email("slamdunk7575@test.com")
                        .build()));

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(UserCreateRequestDto.builder()
                        .name("slamdunk7575")
                        .email("slamdunk7575@test.com")
                        .build())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponseDto.class)
                .value(res -> {
                    assertThat(res.getName()).isEqualTo("slamdunk7575");
                    assertThat(res.getEmail()).isEqualTo("slamdunk7575@test.com");
                });
    }

    @Test
    void findAllUsers() {
        when(userService.findAll()).thenReturn(
                Flux.just(
                        new User(1L, "slamdunk_1", "slamdunk_1@test.com", LocalDateTime.now(), LocalDateTime.now()),
                        new User(1L, "slamdunk_2", "slamdunk_2@test.com", LocalDateTime.now(), LocalDateTime.now()),
                        new User(1L, "slamdunk_3", "slamdunk_3@test.com", LocalDateTime.now(), LocalDateTime.now())
                ));

        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(UserResponseDto.class)
                .hasSize(3);
    }

    @Test
    void findUser() {
        when(userService.findById(1L)).thenReturn(
                Mono.just(new User(1L, "slamdunk7575", "slamdunk7575@test.com", LocalDateTime.now(), LocalDateTime.now()))
        );

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UserResponseDto.class)
                .value(res -> {
                    assertThat(res.getName()).isEqualTo("slamdunk7575");
                    assertThat(res.getEmail()).isEqualTo("slamdunk7575@test.com");
                });
    }

    @Test
    void notFoundUser() {
        when(userService.findById(1L)).thenReturn(
                Mono.empty()
        );

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    void deleteUser() {
        when(userService.deleteById(1L)).thenReturn(
                Mono.empty()
        );

        webTestClient.delete().uri("/users/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void updateUser() {
        when(userService.update(1L, "slamdunk7575", "slamdunk7575@test.com"))
                .thenReturn(Mono.just(User.builder()
                        .id(1L)
                        .name("slamdunk7575")
                        .email("slamdunk7575@test.com")
                        .build()));

        webTestClient.put().uri("/users/1")
                .bodyValue(UserUpdateRequestDto.builder()
                        .name("slamdunk7575")
                        .email("slamdunk7575@test.com")
                        .build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UserResponseDto.class)
                .value(res -> {
                    assertThat(res.getName()).isEqualTo("slamdunk7575");
                    assertThat(res.getEmail()).isEqualTo("slamdunk7575@test.com");
                });
    }
}
