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
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountRepository implements IBankAccountRepository {
		
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("mongodb.provider0.template")
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public Mono<UnitResult<BankAccount>> create(BankAccount entity) {					
		Query query = new Query(Criteria.where("accountNumber").is(entity.getAccountNumber())); 
		Mono<UnitResult<BankAccount>> result = mongoTemplate.exists(query, BankAccount.class).flatMap(i->
		{								
			if(!i.booleanValue())
				return mongoTemplate.insert(entity).map(ii-> new UnitResult<BankAccount>(ii))
						.onErrorResume(e->{
								logger.error(e.getMessage());
								return Mono.just(new UnitResult<BankAccount>(true,e.getMessage()));
							});
			else
				return Mono.just(new UnitResult<BankAccount>(true,"exists bank account!"));					
		});		
		return result;
	}
	
	@Override
	public Mono<UnitResult<BankAccount>> update(BankAccount entity) {								
		Query query = new Query(Criteria.where("accountNumber").is(entity.getAccountNumber())); 
		Mono<UnitResult<BankAccount>> result = mongoTemplate.exists(query, BankAccount.class).flatMap(i->
		{								
			if(i.booleanValue())
				return mongoTemplate.save(entity).map(ii-> new UnitResult<BankAccount>(ii))
						.onErrorResume(e-> {
								logger.error(e.getMessage());
								return Mono.just(new UnitResult<BankAccount>(true,e.getMessage()));
							});				
			else
				return Mono.just(new UnitResult<BankAccount>(true,"bank account not exists!"));					
		});
		return result;
	}

	@Override
	public Mono<UnitResult<BankAccount>> saveAll(Flux<BankAccount> entities) {			
		Mono<UnitResult<BankAccount>> result = mongoTemplate.insertAll(entities.collectList(), BankAccount.class).collectList().map(i-> new UnitResult<BankAccount>(i))
				.onErrorResume(e-> {
						logger.error(e.getMessage());
						return Mono.just(new UnitResult<BankAccount>(true,e.getMessage()));
					});	
		return result;
	}

	@Override
	public Mono<UnitResult<BankAccount>> findById(String id) {
		Mono<UnitResult<BankAccount>> result = mongoTemplate.findById(id, BankAccount.class).map(i-> new UnitResult<BankAccount>(i))
				.onErrorResume(e-> {
						logger.error(e.getMessage());
						return Mono.just(new UnitResult<BankAccount>(true,e.getMessage()));
					});		
		return result;
	}

	@Override
	public Mono<UnitResult<BankAccount>> findByClientIdentNum(String dni) {		
		Query query = new Query(Criteria.where("clientIdentNum").is(dni));
		Mono<UnitResult<BankAccount>> result = mongoTemplate.find(query, BankAccount.class).collectList().map(i-> new UnitResult<BankAccount>(i))
				.onErrorResume(e-> {
						logger.error(e.getMessage());
						return Mono.just(new UnitResult<BankAccount>(true,e.getMessage()));
					});	
		return result;
	}

	@Override
	public Mono<UnitResult<BankAccount>> findByAccountNumber(String accountNumber) {		
		Query query = new Query(Criteria.where("accountNumber").is(accountNumber));			
		Mono<UnitResult<BankAccount>> result = mongoTemplate.findOne(query, BankAccount.class).map(i-> new UnitResult<BankAccount>(i))
				.onErrorResume(e-> {
						logger.error(e.getMessage());
						return Mono.just(new UnitResult<BankAccount>(true,e.getMessage()));
					});		
		return result;
	}

	@Override
	public Mono<UnitResult<BankAccount>> findAll() {	
		Mono<UnitResult<BankAccount>> result = mongoTemplate.findAll(BankAccount.class).collectList().map(i-> new UnitResult<BankAccount>(i))
				.onErrorResume(e-> {
						logger.error(e.getMessage());
						return Mono.just(new UnitResult<BankAccount>(true,e.getMessage()));
					});		
		return result;		
	}

	@Override
	public Mono<ResultBase> deleteById(String id) {				
		Query query = new Query(Criteria.where("Id").is(id));
		Mono<ResultBase> result = mongoTemplate.remove(query,BankAccount.class).flatMap(i-> Mono.just(new ResultBase(false, null)))
				.onErrorResume(e-> {
						logger.error(e.getMessage());
						return Mono.just(new UnitResult<BankAccount>(true,e.getMessage()));
					});		
		return result;
	}

	@Override
	public Flux<BankAccount> findAllStreaming() {
		return mongoTemplate.findAll(BankAccount.class);
	}	
}
