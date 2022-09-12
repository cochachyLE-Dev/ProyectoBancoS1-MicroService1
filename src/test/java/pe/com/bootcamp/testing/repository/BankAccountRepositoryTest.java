package pe.com.bootcamp.testing.repository;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pe.com.bootcamp.domain.aggregate.BankAccount;
import pe.com.bootcamp.domain.repository.IBankAccountRepository;
import reactor.test.StepVerifier;

@SpringBootTest
public class BankAccountRepositoryTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IBankAccountRepository bankAccountReposity;
	
	private BankAccount bankAccount = new BankAccount();
	
	@BeforeEach
	public void setUp() {
		bankAccount.setId("9");
		bankAccount.setAccountNumber("123-4434-5534-569");
		bankAccount.setClientIdentNum("03720853");
		bankAccount.setCreationDate(new Date());						
	}
	
	@Test
	public void create() {
		StepVerifier
		    .create(bankAccountReposity.create(bankAccount))
		    .assertNext(e -> {
		    	logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
		        Assertions.assertFalse(e.isIbException());
		        Assertions.assertTrue(e.getValue() != null && e.getValue().getAccountNumber() == bankAccount.getAccountNumber());
		    }).verifyComplete();
	}
	
	@Test
	public void update() {
		StepVerifier
		    .create(bankAccountReposity.update(bankAccount))
		    .assertNext(e -> {
		    	logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
		        Assertions.assertFalse(e.isIbException());
		        Assertions.assertTrue(e.getValue() != null && e.getValue().getAccountNumber() == bankAccount.getAccountNumber());
		    }).verifyComplete();
	}
	
	@Test
	public void deleteById() {
		StepVerifier
		    .create(bankAccountReposity.deleteById(bankAccount.getId()))
		    .assertNext(e -> {
		    	logger.info("IbException: " + e.isIbException() + " Message: " + e.getMessage());
		        Assertions.assertFalse(e.isIbException());		        
		    }).verifyComplete();
	}
}
