package com.seal.reactive;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.seal.reactive.controller.AccountController;
import com.seal.reactive.model.Account;
import com.seal.reactive.service.AccountReactiveService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = AccountController.class)
class ReactiveApplicationTests {
	
	@Autowired
	  private WebTestClient webClient;
	
	@MockBean
	private AccountReactiveService accountService;
	
	@MockBean
	private KafkaTemplate<String, Account> kafkaTemplate;
	
	@Test
	public void givenId_whenGetAccount_thenFindAccount() {
		Account account = new Account("5645", "Seal", 56.1);
		
		when(accountService.getAccount("5645")).thenReturn( Mono.just(account));
		
		webClient.get()
		.uri("/account/5645")
		.exchange()
		.expectStatus().isOk()
		.expectBody(Account.class);
	}
	
	@Test
	public void whenGetAll_thenFindAllAccounts() {
		List<Account> accounts = Arrays.asList(new Account("5645", "Seal", 56.1), new Account("5646", "Exef", 29.7));
		
		when(accountService.getAll()).thenReturn(Flux.fromIterable(accounts));
		
		webClient.get()
		.uri("/account")
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(Account.class);
	}
	
	@Test
	public void whenPostAccount_thenCreateAccountAndSendNotification() {
		Account account = new Account("5645", "Seal", 56.1);
		
		when(accountService.create(any())).thenReturn(Mono.just(account));
		
		webClient.post()
		.uri("/account/add")
		.body(BodyInserters.fromValue(account))
		.exchange()
		.expectStatus().isOk();
		
		verify(kafkaTemplate).sendDefault(account);
	}

}
