package pe.com.bootcamp.testing.webclient;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import pe.com.bootcamp.domain.aggregate.BankAccount;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class BankAccountWebClientTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
	private WebClient webClient;
	private BankAccount bankAccount = new BankAccount();
	
	private final static String baseUrl = "http://localhost:62908/BankAccount";
	
	@BeforeEach
	private void setUp() {
	    webClient = WebClient.builder()
	              .baseUrl(baseUrl)
	              .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	              .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)	              
	              .build();
	    	    
		bankAccount.setId("19");
		bankAccount.setAccountNumber("943-4534-5534-279");
		bankAccount.setClientIdentNum("03720853");
		bankAccount.setCreationDate(new Date());	
	}

	@Test
	public void create() {				
		StepVerifier
		.create(webClient.post()
				.uri("/").bodyValue(bankAccount).retrieve()
				.bodyToMono(new ParameterizedTypeReference<UnitResult<BankAccount>>(){}))
		.assertNext(e -> {
			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
			Assertions.assertFalse(e.isIbException());
			Assertions.assertTrue(e.getValue() != null && e.getValue().getAccountNumber() == bankAccount.getAccountNumber());
		}).verifyComplete();
	}
	
	@Test
	public void update() {
		StepVerifier
		.create(webClient.put().uri("/")
				.bodyValue(bankAccount)
				.retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<BankAccount>>(){}))
		.assertNext(e -> {
			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
			Assertions.assertFalse(e.isIbException());
			Assertions.assertTrue(e.getValue() != null && e.getValue().getAccountNumber() == bankAccount.getAccountNumber());
		}).verifyComplete();
	}
	
	@Test
	public void findAll() {
		StepVerifier
		.create(webClient.get().uri("/").retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<BankAccount>>(){}))
		.assertNext(e -> {
			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
			Assertions.assertFalse(e.isIbException());
			Assertions.assertTrue(e.getList() != null && e.getList().size() > 0);
		}).verifyComplete();
	}
	
	@Test
	public void findById() {
		StepVerifier
		.create(webClient.get().uri("/{id}", bankAccount.getId())
				.retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<BankAccount>>(){}))
		.assertNext(e -> {
			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
			Assertions.assertFalse(e.isIbException());
			Assertions.assertTrue(e.getList() != null && e.getList().size() > 0);
		}).verifyComplete();
	}
	
	@Test
	public void deleteById() {		
		StepVerifier
		.create(webClient.delete().uri("/{id}", bankAccount.getId()).retrieve().bodyToMono(ResultBase.class))
		.assertNext(e -> {
			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
			Assertions.assertFalse(e.isIbException());			
		}).verifyComplete();
	}
	
	@Test
	public void saveAll() {
		Flux<BankAccount> entities = Flux.just(bankAccount);
		StepVerifier
		.create(webClient.post().uri("/batch")
				.bodyValue(entities)
				.retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<BankAccount>>(){}))
		.assertNext(e -> {
			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
			Assertions.assertFalse(e.isIbException());
			Assertions.assertTrue(e.getValue() != null && e.getValue().getAccountNumber() == bankAccount.getAccountNumber());
		}).verifyComplete();
	}
}
