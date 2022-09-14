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
//import pe.com.bootcamp.domain.aggregate.Client;
//import pe.com.bootcamp.utilities.ResultBase;
//import pe.com.bootcamp.utilities.UnitResult;
//import reactor.core.publisher.Flux;
//import reactor.test.StepVerifier;
//
//@SpringBootTest
//public class ClientWebClientTest {
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//		
//	private WebClient webClient;
//	private Client client = new Client();
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
//		client.setId("1");		
//		client.setDni("03720853");
//		client.setCreationDate(new Date());	
//	}
//
//	@Test
//	public void create() {				
//		StepVerifier
//		.create(webClient.post()
//				.uri("/").bodyValue(client).retrieve()
//				.bodyToMono(new ParameterizedTypeReference<UnitResult<Client>>(){}))
//		.assertNext(e -> {
//			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//			Assertions.assertFalse(e.isIbException());
//			Assertions.assertTrue(e.getValue() != null && e.getValue().getDni() == client.getDni());
//		}).verifyComplete();
//	}
//	
//	@Test
//	public void update() {
//		StepVerifier
//		.create(webClient.put().uri("/")
//				.bodyValue(client)
//				.retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<Client>>(){}))
//		.assertNext(e -> {
//			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//			Assertions.assertFalse(e.isIbException());
//			Assertions.assertTrue(e.getValue() != null && e.getValue().getDni() == client.getDni());
//		}).verifyComplete();
//	}
//	
//	@Test
//	public void findAll() {
//		StepVerifier
//		.create(webClient.get().uri("/").retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<Client>>(){}))
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
//		.create(webClient.get().uri("/{id}", client.getId())
//				.retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<Client>>(){}))
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
//		.create(webClient.delete().uri("/{id}", client.getId()).retrieve().bodyToMono(ResultBase.class))
//		.assertNext(e -> {
//			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//			Assertions.assertFalse(e.isIbException());			
//		}).verifyComplete();
//	}
//	
//	@Test
//	public void saveAll() {
//		Flux<Client> entities = Flux.just(client);
//		StepVerifier
//		.create(webClient.post().uri("/batch")
//				.bodyValue(entities)
//				.retrieve().bodyToMono(new ParameterizedTypeReference<UnitResult<Client>>(){}))
//		.assertNext(e -> {
//			logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//			Assertions.assertFalse(e.isIbException());
//			Assertions.assertTrue(e.getValue() != null && e.getValue().getDni() == client.getDni());
//		}).verifyComplete();
//	}
//}
