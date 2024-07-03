package com.yanggang.webflux.service;

import com.yanggang.webflux.repository.User;
import com.yanggang.webflux.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> create(String name, String email) {
        return userRepository.save(
                User.builder()
                        .name(name)
                        .email(email)
                        .build()
        );
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<Integer> deleteById(Long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<?> update(Long id, String name, String email) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(name);
                    user.setEmail(email);
                    return userRepository.save(user);
                });
    }
}
