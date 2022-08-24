package pe.com.bootcamp.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.bootcamp.domain.aggregate.BankAccount;
import pe.com.bootcamp.domain.repository.BankAccountRepository;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/BankAccount")
public class BankAccountController {

	@Autowired
	BankAccountRepository bankAccountRepository; 
		
	@RequestMapping(value = "/", method = RequestMethod.POST)
	Mono<UnitResult<BankAccount>> create(@RequestBody BankAccount entity){
		return bankAccountRepository.create(entity);
	}
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	Mono<UnitResult<BankAccount>> update(@RequestBody BankAccount entity){
		return bankAccountRepository.update(entity);
	}
	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	Mono<UnitResult<BankAccount>> saveAll(@RequestBody Flux<BankAccount> entities){
		return bankAccountRepository.saveAll(entities);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Mono<UnitResult<BankAccount>> findById(@PathVariable String id){
		return bankAccountRepository.findById(id);
	}
	@RequestMapping(value = "/{dni}", method = RequestMethod.GET)
	Mono<UnitResult<BankAccount>> findByClientIdentNum(@PathVariable String dni){
		return bankAccountRepository.findByClientIdentNum(dni);
	}
	@RequestMapping(value = "/{accountNumber}", method = RequestMethod.POST)
	Mono<UnitResult<BankAccount>> findByAccountNumber(@PathVariable String accountNumber){
		return bankAccountRepository.findByAccountNumber(accountNumber);
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	Mono<UnitResult<BankAccount>> findAll(){
		return bankAccountRepository.findAll();
	}	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	Mono<ResultBase> deleteById(@PathVariable String id){
		return bankAccountRepository.deleteById(id);
	}
	@RequestMapping(value = "/stream", method = RequestMethod.DELETE,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<BankAccount> findAllStreaming(@PathVariable String id){
		return bankAccountRepository.findAllStreaming().delayElements(Duration.ofSeconds(1));
	}
}
