package com.yanggang.webflux.repository;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Test
    void save() {
        var user = User.builder()
                .name("slamdunk7575")
                .email("slamdunk7575@test.com")
                .build();

        StepVerifier.create(userRepository.save(user))
                .assertNext(u -> {
                    assertThat(u.getName()).isEqualTo("slamdunk7575");
                    assertThat(u.getEmail()).isEqualTo("slamdunk7575@test.com");
                })
                .verifyComplete();
    }

    @Test
    void findAll() {
        userRepository.save(User.builder()
                .name("slamdunk_1")
                .email("slamdunk_1@test.com").build());

        userRepository.save(User.builder()
                .name("slamdunk_2")
                .email("slamdunk_2@test.com").build());

        userRepository.save(User.builder()
                .name("slamdunk_3")
                .email("slamdunk_3@test.com").build());

        StepVerifier.create(userRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        userRepository.save(User.builder()
                .id(1L)
                .name("slamdunk_1")
                .email("slamdunk_1@test.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        StepVerifier.create(userRepository.findById(1L))
                .assertNext(u -> {
                    assertThat(u.getName()).isEqualTo("slamdunk_1");
                    assertThat(u.getEmail()).isEqualTo("slamdunk_1@test.com");
                })
                .verifyComplete();
    }

    @Test
    void deleteById() {
        userRepository.save(User.builder()
                .name("slamdunk_1")
                .email("slamdunk_1@test.com").build());

        userRepository.save(User.builder()
                .name("slamdunk_2")
                .email("slamdunk_2@test.com").build());

        StepVerifier.create(userRepository.deleteById(2L))
                .expectNextCount(1)
                .verifyComplete();
    }
}
