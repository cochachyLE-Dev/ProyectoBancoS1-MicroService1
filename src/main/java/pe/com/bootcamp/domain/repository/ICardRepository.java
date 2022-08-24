package pe.com.bootcamp.domain.repository;

import pe.com.bootcamp.domain.aggregate.Card;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICardRepository //extends ReactiveMongoRepository<Card, String> 
{     
    Mono<UnitResult<Card>> create(Card entity);
	
	Mono<UnitResult<Card>> update(Card entity);
	
	Mono<UnitResult<Card>> saveAll(Flux<Card> entities);
	
	Mono<UnitResult<Card>> findById(String id);
	
	Mono<UnitResult<Card>> findByClientIdentNum(String dni);
	
	Mono<UnitResult<Card>> findByCardNumber(String cardNumber);
	
	Mono<UnitResult<Card>> findAll();	
	
	Mono<ResultBase> deleteById(String id);
}
