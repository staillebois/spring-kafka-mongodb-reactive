package com.seal.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.seal.reactive.controller.AccountController;
import com.seal.reactive.model.Account;
import com.seal.reactive.repository.AccountReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class ReactiveApplicationTests {
	
	@Autowired
	private AccountController accountController;
	
	@Test
	public void givenValue_whenFindAllByValue_thenFindAccount() {
		Mono<Account> updateMono = accountController.createAccount(new Account(null, "Seal", 28.6));

	    StepVerifier
	      .create(updateMono)
	      .assertNext(account -> {
	          assertEquals("Seal", account.getOwner());
	          assertEquals(Double.valueOf(28.6) , account.getValue());
	          assertNotNull(account.getId());
	      })
	      .expectComplete()
	      .verify();
	}

}
