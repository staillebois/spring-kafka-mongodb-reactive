package com.seal.reactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.seal.reactive.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountReactiveRepository extends ReactiveMongoRepository<Account, String> {
	Flux<Account> findAllByValue(Double value);
    Mono<Account> findFirstByOwner(Mono<String> owner);
}
