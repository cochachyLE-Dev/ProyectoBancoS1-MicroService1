package pe.com.bootcamp.domain.repository;

import pe.com.bootcamp.domain.aggregate.Client;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientRepository {//extends ReactiveMongoRepository<Client, String>{

	Mono<UnitResult<Client>> create(Client entity);
	
	Mono<UnitResult<Client>> update(Client entity);
	
	Mono<UnitResult<Client>> saveAll(Flux<Client> entities);
	
	Mono<UnitResult<Client>> findById(String id);
	
	Mono<UnitResult<Client>> findByIdentNum(String dni);
	
	Mono<UnitResult<Client>> findAll();	
	
	Mono<ResultBase> deleteById(String id);	
}
