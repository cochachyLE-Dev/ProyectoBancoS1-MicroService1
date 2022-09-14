//package pe.com.bootcamp.testing.webclient;
//
//import java.util.Date;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import pe.com.bootcamp.domain.aggregate.Card;
//import pe.com.bootcamp.utilities.ResultBase;
//import pe.com.bootcamp.utilities.UnitResult;
//import reactor.core.publisher.Flux;
//import reactor.test.StepVerifier;
//
//@SpringBootTest
//public class BankCardWebClientTest {
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//		
//	private WebClient webClient;
//	private Card bankCard = new Card();
//	
//	private final static String baseUrl = "http://localhost:62908/BankAccount";
//	
//	@BeforeEach
//	private void setUp() {
//	    webClient = WebClient.builder()
//	              .baseUrl(baseUrl)
//	              .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//	              .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)	              
//	              .build();
//	    	    
//		bankCard.setId("19");
//		bankCard.setCardNumber("943-4534-5534-279");		
//		bankCard.setCreationDate(new Date());	
//	}
//
//	@Test
//	public void create() {				
//		StepVerifier
//		.create(webClient.post()
//				.uri("/").bodyValue(bankCard).retrieve()
//				.bodyToMono(new ParameterizedTypeReference<UnitResult<Card>>(){}))
//		.assertNext(e -> {
//			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//			Assertions.assertFalse(e.isIbException());
//			Assertions.assertTrue(e.getValue() != null && e.getValue().getCardNumber() == bankCard.getCardNumber());
//		}).verifyComplete();
//	}
//	
//	@Test
//	public void update() {
//		StepVerifier
//		.create(webClient.put().uri("/")
//				.bodyValue(bankCard)
//				.retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<Card>>(){}))
//		.assertNext(e -> {
//			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//			Assertions.assertFalse(e.isIbException());
//			Assertions.assertTrue(e.getValue() != null && e.getValue().getCardNumber() == bankCard.getCardNumber());
//		}).verifyComplete();
//	}
//	
//	@Test
//	public void findAll() {
//		StepVerifier
//		.create(webClient.get().uri("/").retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<Card>>(){}))
//		.assertNext(e -> {
//			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//			Assertions.assertFalse(e.isIbException());
//			Assertions.assertTrue(e.getList() != null && e.getList().size() > 0);
//		}).verifyComplete();
//	}
//	
//	@Test
//	public void findById() {
//		StepVerifier
//		.create(webClient.get().uri("/{id}", bankCard.getId())
//				.retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<Card>>(){}))
//		.assertNext(e -> {
//			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//			Assertions.assertFalse(e.isIbException());
//			Assertions.assertTrue(e.getList() != null && e.getList().size() > 0);
//		}).verifyComplete();
//	}
//	
//	@Test
//	public void deleteById() {		
//		StepVerifier
//		.create(webClient.delete().uri("/{id}", bankCard.getId()).retrieve().bodyToMono(ResultBase.class))
//		.assertNext(e -> {
//			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//			Assertions.assertFalse(e.isIbException());			
//		}).verifyComplete();
//	}
//	
//	@Test
//	public void saveAll() {
//		Flux<Card> entities = Flux.just(bankCard);
//		StepVerifier
//		.create(webClient.post().uri("/batch")
//				.bodyValue(entities)
//				.retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<Card>>(){}))
//		.assertNext(e -> {
//			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//			Assertions.assertFalse(e.isIbException());
//			Assertions.assertTrue(e.getList() != null && e.getList().size() > 0);
//		}).verifyComplete();
//	}
//}
