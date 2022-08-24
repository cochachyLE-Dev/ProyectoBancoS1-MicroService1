package pe.com.bootcamp.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public Mono<UnitResult<Client>> create(Client entity) {
		Mono<UnitResult<Client>> result;
		try {
			Query query = new Query(Criteria.where("dni").is(entity.getDni())); 
			result = mongoTemplate.exists(query, Client.class).flatMap(i->
			{								
				if(!i.booleanValue())
					return mongoTemplate.insert(entity).map(ii-> new UnitResult<Client>(ii));				
				else
					return Mono.just(new UnitResult<Client>(true,"exists bank account!"));					
			});
		}catch (Exception e) {
			result = Mono.just(new UnitResult<Client>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Client>> update(Client entity) {
		Mono<UnitResult<Client>> result;
		try {			
			Query query = new Query(Criteria.where("dni").is(entity.getDni())); 
			result = mongoTemplate.exists(query, Client.class).flatMap(i->
			{								
				if(i.booleanValue())
					return mongoTemplate.save(entity).map(ii-> new UnitResult<Client>(ii));				
				else
					return Mono.just(new UnitResult<Client>(true,"bank account not exists!"));					
			});
		}catch (Exception e) {
			result = Mono.just(new UnitResult<Client>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Client>> saveAll(Flux<Client> entities) {
		Mono<UnitResult<Client>> result;
		try {
			result = mongoTemplate.insertAll(entities.collectList(), Client.class).collectList().map(i-> new UnitResult<Client>(i));
		}catch (Exception e) {
			result = Mono.just(new UnitResult<Client>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Client>> findById(String id) {
		Mono<UnitResult<Client>> result;
		try {
			result = mongoTemplate.findById(id, Client.class).map(i-> new UnitResult<Client>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Client>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Client>> findByIdentNum(String dni) {
		Mono<UnitResult<Client>> result;			
		try {			
			Query query = new Query(Criteria.where("ClientId").is(dni));
			result = mongoTemplate.find(query, Client.class).collectList().map(i-> new UnitResult<Client>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Client>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Client>> findAll() {
		Mono<UnitResult<Client>> result;			
		try {						
			result = mongoTemplate.findAll(Client.class).collectList().map(i-> new UnitResult<Client>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Client>(true,e.getMessage()));
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
