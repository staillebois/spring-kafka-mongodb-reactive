package com.seal.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seal.reactive.configuration.KafkaTopicConfiguration;
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
	public Mono<Account> getAccountById(@PathVariable String id) {
	    return accountService.getAccountById(id);
	}
	
	@GetMapping
	public Flux<Account> getAllAccount() {
	    return accountService.getAllAccount();
	}
	
	@PostMapping("/add")
	public Mono<Account> createAccount(@RequestBody Account account) {
	    return accountService.createAccount(account).doOnNext(this::sendNotification);
	}
	
	private void sendNotification(Account account) {
		kafkaTemplate.send(KafkaTopicConfiguration.TOPIC, account);
	}
}
