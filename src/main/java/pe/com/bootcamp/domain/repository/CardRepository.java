package pe.com.bootcamp.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public Mono<UnitResult<Card>> create(Card entity) {
		Mono<UnitResult<Card>> result;
		try {
			Query query = new Query(Criteria.where("cardNumber").is(entity.getCardNumber())); 
			result = mongoTemplate.exists(query, Card.class).flatMap(i->
			{								
				if(!i.booleanValue())
					return mongoTemplate.insert(entity).map(ii-> new UnitResult<Card>(ii));				
				else
					return Mono.just(new UnitResult<Card>(true,"exists card!"));					
			});
		}catch (Exception e) {
			result = Mono.just(new UnitResult<Card>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> update(Card entity) {
		Mono<UnitResult<Card>> result;
		try {			
			Query query = new Query(Criteria.where("cardNumber").is(entity.getCardNumber())); 
			result = mongoTemplate.exists(query, Card.class).flatMap(i->
			{								
				if(i.booleanValue())
					return mongoTemplate.save(entity).map(ii-> new UnitResult<Card>(ii));				
				else
					return Mono.just(new UnitResult<Card>(true,"bank account not exists!"));					
			});
		}catch (Exception e) {
			result = Mono.just(new UnitResult<Card>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> saveAll(Flux<Card> entities) {
		Mono<UnitResult<Card>> result;
		try {
			result = mongoTemplate.insertAll(entities.collectList(), Card.class).collectList().map(i-> new UnitResult<Card>(i));
		}catch (Exception e) {
			result = Mono.just(new UnitResult<Card>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> findById(String id) {
		Mono<UnitResult<Card>> result;
		try {
			result = mongoTemplate.findById(id, Card.class).map(i-> new UnitResult<Card>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Card>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> findByClientIdentNum(String dni) {
		Mono<UnitResult<Card>> result;			
		try {			
			Query query = new Query(Criteria.where("ClientIdentNum").is(dni));
			result = mongoTemplate.find(query, Card.class).collectList().map(i-> new UnitResult<Card>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Card>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> findByCardNumber(String cardNumber) {
		Mono<UnitResult<Card>> result;			
		try {			
			Query query = new Query(Criteria.where("cardNumber").is(cardNumber));			
			result = mongoTemplate.findOne(query, Card.class).map(i-> new UnitResult<Card>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Card>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Card>> findAll() {
		Mono<UnitResult<Card>> result;			
		try {						
			result = mongoTemplate.findAll(Card.class).collectList().map(i-> new UnitResult<Card>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Card>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<ResultBase> deleteById(String id) {
		Mono<ResultBase> result;			
		try {
			Query query = new Query(Criteria.where("Id").is(id));
			result = mongoTemplate.remove(query,BankAccount.class).flatMap(i-> Mono.just(new ResultBase(i.getDeletedCount() > 0, null)));
		}catch (Exception e) {
			result = Mono.just(new ResultBase(true,e.getMessage()));
		}
		return result;
	}	
}
