package com.yanggang.webflux.service;

import com.yanggang.webflux.repository.User;
import com.yanggang.webflux.repository.UserR2dbcRepository;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class UserServiceImpl implements UserService {

    // private final UserRepository userRepository;
    private final UserR2dbcRepository userR2dbcRepository;
    private final ReactiveRedisTemplate<String, User> reactiveRedisTemplate;

    public UserServiceImpl(UserR2dbcRepository userR2dbcRepository,
                           ReactiveRedisTemplate<String, User> reactiveRedisTemplate) {
        this.userR2dbcRepository = userR2dbcRepository;
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    @Override
    public Mono<User> create(String name, String email) {
        return userR2dbcRepository.save(
                User.builder()
                        .name(name)
                        .email(email)
                        .build()
        );
    }

    @Override
    public Flux<User> findAll() {
        return userR2dbcRepository.findAll();
    }

    @Override
    public Mono<User> findById(Long id) {
        // Redis 조회
        // 값이 존재하면 응답
        // 없으면 DB 질의하고 결과 Redis 저장
        return reactiveRedisTemplate.opsForValue()
                .get(getUserCacheKey(id))
                .switchIfEmpty(userR2dbcRepository.findById(id)
                        .flatMap(user -> reactiveRedisTemplate.opsForValue()
                                .set(getUserCacheKey(id), user, Duration.ofSeconds(60))
                                .then(Mono.just(user))
                        )
                );
    }

    private static String getUserCacheKey(Long id) {
        return "users:%d".formatted(id);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        // 삭제시 DB 및 Redis 삭제
        return userR2dbcRepository.deleteById(id)
                .then(reactiveRedisTemplate.unlink(getUserCacheKey(id))
                        .then(Mono.empty())
                );
    }

    @Override
    public Mono<User> update(Long id, String name, String email) {
        // 업데이트 되면 DB 업데이트 저장
        // Redis 캐시 key 삭제 (비동기)
        return userR2dbcRepository.findById(id)
                .flatMap(user -> {
                    user.setName(name);
                    user.setEmail(email);
                    return userR2dbcRepository.save(user);
                })
                .flatMap(user -> reactiveRedisTemplate.unlink(getUserCacheKey(id))
                        .then(Mono.just(user))
                );
    }
}
