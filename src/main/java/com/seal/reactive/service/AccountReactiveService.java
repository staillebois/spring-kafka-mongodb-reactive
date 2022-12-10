package com.seal.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seal.reactive.model.Account;
import com.seal.reactive.repository.AccountReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountReactiveService {

	@Autowired
	AccountReactiveRepository accountRepository;
	
	public Mono<Account> getAccount(String id) {
		return accountRepository.findById(id);
	}
	
	public Flux<Account> getAll() {
	    return accountRepository.findAll();
	}
	
	public Mono<Account> create(Account account) {
	    return accountRepository.save(account);
	}
}
