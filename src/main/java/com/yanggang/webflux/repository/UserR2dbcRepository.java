package com.yanggang.webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserR2dbcRepository extends ReactiveCrudRepository<User, Long> {
}
