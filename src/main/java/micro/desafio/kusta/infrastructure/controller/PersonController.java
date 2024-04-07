package micro.desafio.kusta.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import micro.desafio.kusta.application.service.PersonService;
import micro.desafio.kusta.domain.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/person")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Person>>> findAll(){
		return Mono.just(
			 ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(service.findAll()));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Person>> save(@RequestBody Person person){
		//Mono<Person> request =
			return service.save(person).map(c -> {
				return ResponseEntity
						.created(URI.create("api/person/".concat(c.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(c);
			});
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Person>> findById(@PathVariable String id){
		return service.findById(id).map(c -> 
			 ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(c)
		).defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping
	public Mono<ResponseEntity<Person>> update(@RequestBody Person person){
			return service.update(person).map(c -> {
				return ResponseEntity
						.created(URI.create("api/person/".concat(c.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(c);
			});
	}
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
			return service.findById(id).flatMap(c -> {
				return service.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
			}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

}
