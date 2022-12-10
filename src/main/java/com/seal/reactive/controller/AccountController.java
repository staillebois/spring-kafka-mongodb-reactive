package com.seal.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.seal.reactive.model.Account;
import com.seal.reactive.service.AccountReactiveService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountReactiveService accountService;
	
	@Autowired
	private KafkaTemplate<String, Account> kafkaTemplate;

	
	@GetMapping("/{id}")
	public Mono<Account> getAccount(@PathVariable String id) {
	    return accountService.getAccount(id);
	}
	
	@GetMapping
	public Flux<Account> getAll() {
	    return accountService.getAll();
	}
	
	@PostMapping("/add")
	public Mono<Account> create(@RequestBody Account account) {
	    return accountService.create(account).doOnNext(this::sendNotification);
	}
	
	private void sendNotification(Account account) {
		kafkaTemplate.sendDefault(account);
	}
}
