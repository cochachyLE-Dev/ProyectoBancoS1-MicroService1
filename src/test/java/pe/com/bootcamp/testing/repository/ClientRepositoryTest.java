//package pe.com.bootcamp.testing.repository;
//
//import java.util.Date;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import pe.com.bootcamp.domain.aggregate.Client;
//import pe.com.bootcamp.domain.repository.IClientRepository;
//import reactor.test.StepVerifier;
//
//@SpringBootTest
//public class ClientRepositoryTest {
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//	
//	@Autowired
//	private IClientRepository clientReposity;
//	
//	private Client client = new Client();
//	
//	@BeforeEach
//	public void setUp() {
//		client.setId("9");		
//		client.setDni("03720853");
//		client.setCreationDate(new Date());						
//	}
//	
//	@Test
//	public void create() {
//		StepVerifier
//		    .create(clientReposity.create(client))
//		    .assertNext(e -> {
//		    	logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//		        Assertions.assertFalse(e.isIbException());
//		        Assertions.assertTrue(e.getValue() != null && e.getValue().getDni() == client.getDni());
//		    }).verifyComplete();
//	}
//	
//	@Test
//	public void update() {
//		StepVerifier
//		    .create(clientReposity.update(client))
//		    .assertNext(e -> {
//		    	logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//		        Assertions.assertFalse(e.isIbException());
//		        Assertions.assertTrue(e.getValue() != null && e.getValue().getDni() == client.getDni());
//		    }).verifyComplete();
//	}
//	
//	@Test
//	public void deleteById() {
//		StepVerifier
//		    .create(clientReposity.deleteById(client.getId()))
//		    .assertNext(e -> {
//		    	logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
//		        Assertions.assertFalse(e.isIbException());		        
//		    }).verifyComplete();
//	}
//}
