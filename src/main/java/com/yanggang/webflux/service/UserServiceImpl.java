package com.yanggang.webflux.service;

import com.yanggang.webflux.repository.User;
import com.yanggang.webflux.repository.UserR2dbcRepository;
import com.yanggang.webflux.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    // private final UserRepository userRepository;
    private final UserR2dbcRepository userR2dbcRepository;

    public UserServiceImpl(UserR2dbcRepository userR2dbcRepository) {
        this.userR2dbcRepository = userR2dbcRepository;
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
        return userR2dbcRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return userR2dbcRepository.deleteById(id);
    }

    @Override
    public Mono<User> update(Long id, String name, String email) {
        return userR2dbcRepository.findById(id)
                .flatMap(user -> {
                    user.setName(name);
                    user.setEmail(email);
                    return userR2dbcRepository.save(user);
                });
    }
}
