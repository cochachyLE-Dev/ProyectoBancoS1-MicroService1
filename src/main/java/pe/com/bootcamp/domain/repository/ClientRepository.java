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
import pe.com.bootcamp.domain.aggregate.Client;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientRepository implements IClientRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("mongodb.provider0.template")
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public Mono<UnitResult<Client>> create(Client entity) {		
		Query query = new Query(Criteria.where("dni").is(entity.getDni())); 
		Mono<UnitResult<Client>> result = mongoTemplate.exists(query, Client.class).flatMap(i->
		{								
			if(!i.booleanValue())
				return mongoTemplate.insert(entity).map(ii-> new UnitResult<Client>(ii))
						.onErrorResume(e-> {
							logger.error(e.getMessage());
							return Mono.just(new UnitResult<Client>(true,e.getMessage()));
						});
			else
				return Mono.just(new UnitResult<Client>(true,"exists bank account!"));					
		});
		
		return result;
	}

	@Override
	public Mono<UnitResult<Client>> update(Client entity) {
		Query query = new Query(Criteria.where("dni").is(entity.getDni())); 
		Mono<UnitResult<Client>> result = mongoTemplate.exists(query, Client.class).flatMap(i->
		{								
			if(i.booleanValue())
				return mongoTemplate.save(entity).map(ii-> new UnitResult<Client>(ii))
						.onErrorResume(e-> {
							logger.error(e.getMessage());
							return Mono.just(new UnitResult<Client>(true,e.getMessage()));
						});
			else
				return Mono.just(new UnitResult<Client>(true,"bank account not exists!"));					
		});		
		return result;
	}

	@Override
	public Mono<UnitResult<Client>> saveAll(Flux<Client> entities) {
		Mono<UnitResult<Client>> result = mongoTemplate.insertAll(entities.collectList(), Client.class).collectList().map(i-> new UnitResult<Client>(i))
				.onErrorResume(e-> {
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Client>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Client>> findById(String id) {
		Mono<UnitResult<Client>> result = mongoTemplate.findById(id, Client.class).map(i-> new UnitResult<Client>(i))
				.onErrorResume(e-> {
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Client>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Client>> findByIdentNum(String dni) {
		Query query = new Query(Criteria.where("ClientId").is(dni));
		Mono<UnitResult<Client>> result = mongoTemplate.find(query, Client.class).collectList().map(i-> new UnitResult<Client>(i))
				.onErrorResume(e-> {
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Client>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Client>> findAll() {
		Mono<UnitResult<Client>> result = mongoTemplate.findAll(Client.class).collectList().map(i-> new UnitResult<Client>(i))
				.onErrorResume(e-> {
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Client>(true,e.getMessage()));
				});
		return result;	
	}

	@Override
	public Mono<ResultBase> deleteById(String id) {	
		Query query = new Query(Criteria.where("Id").is(id));
		Mono<ResultBase> result = mongoTemplate.remove(query,BankAccount.class).flatMap(i-> Mono.just(new ResultBase(i.getDeletedCount() > 0, null)))
				.onErrorResume(e-> {
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Client>(true,e.getMessage()));
				});
		return result;
	}
}
