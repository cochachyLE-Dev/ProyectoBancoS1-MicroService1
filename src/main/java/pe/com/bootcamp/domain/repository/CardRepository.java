package pe.com.bootcamp.domain.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import pe.com.bootcamp.domain.aggregate.BankAccount;
import pe.com.bootcamp.domain.aggregate.Card;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CardRepository implements ICardRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("mongodb.provider0.template")
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public Mono<UnitResult<Card>> create(Card entity) {				
		Query query = new Query(Criteria.where("cardNumber").is(entity.getCardNumber())); 
		Mono<UnitResult<Card>> result = mongoTemplate.exists(query, Card.class).flatMap(i->
		{								
			if(!i.booleanValue())
				return mongoTemplate.insert(entity).map(ii-> new UnitResult<Card>(ii))
						.onErrorResume(e->{
							logger.error(e.getMessage());
							return Mono.just(new UnitResult<Card>(true,e.getMessage()));
						});
			else
				return Mono.just(new UnitResult<Card>(true,"exists card!"));					
		});
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> update(Card entity) {			
		Query query = new Query(Criteria.where("cardNumber").is(entity.getCardNumber())); 
		Mono<UnitResult<Card>> result = mongoTemplate.exists(query, Card.class).flatMap(i->
		{								
			if(i.booleanValue())
				return mongoTemplate.save(entity).map(ii-> new UnitResult<Card>(ii))
						.onErrorResume(e->{
							logger.error(e.getMessage());
							return Mono.just(new UnitResult<Card>(true,e.getMessage()));
						});
			else
				return Mono.just(new UnitResult<Card>(true,"bank account not exists!"));					
		});
		
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> saveAll(Flux<Card> entities) {
		Mono<UnitResult<Card>> result = mongoTemplate.insertAll(entities.collectList(), Card.class).collectList().map(i-> new UnitResult<Card>(i))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Card>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> findById(String id) {
		Mono<UnitResult<Card>> result = mongoTemplate.findById(id, Card.class).map(i-> new UnitResult<Card>(i))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Card>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> findByClientIdentNum(String dni) {
		Query query = new Query(Criteria.where("ClientIdentNum").is(dni));
		Mono<UnitResult<Card>> result = mongoTemplate.find(query, Card.class).collectList().map(i-> new UnitResult<Card>(i))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Card>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> findByCardNumber(String cardNumber) {
		Query query = new Query(Criteria.where("cardNumber").is(cardNumber));			
		Mono<UnitResult<Card>> result = mongoTemplate.findOne(query, Card.class).map(i-> new UnitResult<Card>(i))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Card>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> findAll() {
		Mono<UnitResult<Card>> result = mongoTemplate.findAll(Card.class).collectList().map(i-> new UnitResult<Card>(i))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Card>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<ResultBase> deleteById(String id) {
		Query query = new Query(Criteria.where("Id").is(id));
		Mono<ResultBase> result = mongoTemplate.remove(query,BankAccount.class).flatMap(i-> Mono.just(new ResultBase(i.getDeletedCount() > 0, null)))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new ResultBase(true,e.getMessage()));
				});
		return result;
	}
}
