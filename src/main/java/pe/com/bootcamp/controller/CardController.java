package pe.com.bootcamp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.bootcamp.domain.aggregate.Card;
import pe.com.bootcamp.domain.repository.CardRepository;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Card")
public class CardController {
	@Autowired
	CardRepository cardRepository; 
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	Mono<UnitResult<Card>> create(@RequestBody Card entity){
		return cardRepository.create(entity);
	}
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	Mono<UnitResult<Card>> update(@RequestBody Card entity){
		return cardRepository.update(entity);
	}
	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	Mono<UnitResult<Card>> saveAll(@RequestBody Flux<Card> entities){
		return cardRepository.saveAll(entities);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Mono<UnitResult<Card>> findById(@PathVariable String id){
		return cardRepository.findById(id);
	}
	@RequestMapping(value = "/{dni}", method = RequestMethod.GET)
	Mono<UnitResult<Card>> findByClientIdentNum(@PathVariable String dni){
		return cardRepository.findByClientIdentNum(dni);
	}
	@RequestMapping(value = "/{cardNumber}", method = RequestMethod.POST)
	Mono<UnitResult<Card>> findByCardNumber(@PathVariable String cardNumber){
		return cardRepository.findByCardNumber(cardNumber);
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	Mono<UnitResult<Card>> findAll(){
		return cardRepository.findAll();
	}	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	Mono<ResultBase> deleteById(@PathVariable String id){
		return cardRepository.deleteById(id);
	}
}
