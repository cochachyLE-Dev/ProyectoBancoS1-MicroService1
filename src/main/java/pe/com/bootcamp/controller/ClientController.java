package pe.com.bootcamp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.bootcamp.domain.aggregate.Client;
import pe.com.bootcamp.domain.repository.IClientRepository;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Client")
public class ClientController {
	@Autowired
	IClientRepository clientRepository; 
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	Mono<UnitResult<Client>> create(@RequestBody Client entity){
		return clientRepository.create(entity);
	}
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	Mono<UnitResult<Client>> update(@RequestBody Client entity){
		return clientRepository.update(entity);
	}
	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	Mono<UnitResult<Client>> saveAll(@RequestBody Flux<Client> entities){
		return clientRepository.saveAll(entities);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Mono<UnitResult<Client>> findById(@PathVariable String id){
		return clientRepository.findById(id);
	}
	@RequestMapping(value = "/{dni}", method = RequestMethod.GET)
	Mono<UnitResult<Client>> findByIdentNum(@PathVariable String dni){
		return clientRepository.findByIdentNum(dni);
	}	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	Mono<UnitResult<Client>> findAll(){
		return clientRepository.findAll();
	}	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	Mono<ResultBase> deleteById(@PathVariable String id){
		return clientRepository.deleteById(id);
	}
}
