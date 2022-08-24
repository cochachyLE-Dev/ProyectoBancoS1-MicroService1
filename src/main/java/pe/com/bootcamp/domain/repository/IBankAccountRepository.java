package pe.com.bootcamp.domain.repository;

import pe.com.bootcamp.domain.aggregate.BankAccount;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountRepository {
	
	Mono<UnitResult<BankAccount>> create(BankAccount entity);
	
	Mono<UnitResult<BankAccount>> update(BankAccount entity);
	
	Mono<UnitResult<BankAccount>> saveAll(Flux<BankAccount> entities);
	
	Mono<UnitResult<BankAccount>> findById(String id);
	
	Mono<UnitResult<BankAccount>> findByClientIdentNum(String dni);
	
	Mono<UnitResult<BankAccount>> findByAccountNumber(String accountNumber);
	
	Mono<UnitResult<BankAccount>> findAll();
	
	Flux<BankAccount> findAllStreaming();
	
	Mono<ResultBase> deleteById(String id);
}
