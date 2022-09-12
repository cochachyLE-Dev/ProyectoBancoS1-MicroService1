package pe.com.bootcamp.testing.repository;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pe.com.bootcamp.domain.aggregate.Card;
import pe.com.bootcamp.domain.repository.ICardRepository;
import reactor.test.StepVerifier;

@SpringBootTest
public class BankCardRepositoryTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ICardRepository bankCardReposity;
	
	private Card bankCard = new Card();
	
	@BeforeEach
	public void setUp() {
		bankCard.setId("9");
		bankCard.setCardNumber("123-4434-5534-569");		
		bankCard.setCreationDate(new Date());						
	}
	
	@Test
	public void create() {
		StepVerifier
		    .create(bankCardReposity.create(bankCard))
		    .assertNext(e -> {
		    	logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
		        Assertions.assertFalse(e.isIbException());
		        Assertions.assertTrue(e.getValue() != null && e.getValue().getCardNumber() == bankCard.getCardNumber());
		    }).verifyComplete();
	}
	
	@Test
	public void update() {
		StepVerifier
		    .create(bankCardReposity.update(bankCard))
		    .assertNext(e -> {
		    	logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
		        Assertions.assertFalse(e.isIbException());
		        Assertions.assertTrue(e.getValue() != null && e.getValue().getCardNumber() == bankCard.getCardNumber());
		    }).verifyComplete();
	}
	
	@Test
	public void deleteById() {
		StepVerifier
		    .create(bankCardReposity.deleteById(bankCard.getId()))
		    .assertNext(e -> {
		    	logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
		        Assertions.assertFalse(e.isIbException());		        
		    }).verifyComplete();
	}
}
