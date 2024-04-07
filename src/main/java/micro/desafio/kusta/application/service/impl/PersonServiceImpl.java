package micro.desafio.kusta.application.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mongodb.MongoException;

import micro.desafio.kusta.application.service.PersonService;
import micro.desafio.kusta.domain.entity.Person;
import micro.desafio.kusta.infrastructure.exception.DatabaseConnectionException;
import micro.desafio.kusta.infrastructure.exception.ResourceNotFoundException;
import micro.desafio.kusta.infrastructure.repository.IPersonRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private IPersonRepository repository;

	@Override
	public Flux<Person> findAll() {
		return repository.findAll()
	             .switchIfEmpty(Mono.error(new ResourceNotFoundException("Personas no encontradas" )))
				 .onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));
		
	}

	@Override
	public Mono<Person> findById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id)
	             .switchIfEmpty(Mono.error(new ResourceNotFoundException("Persona no encontrada con id: " + id)))
				 .onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));
	}

	@Override
	public Mono<Person> save(Person entity) {
		// TODO Auto-generated method stub
		entity.setId(UUID.randomUUID().toString());
		return repository.save(entity)
	             .onErrorMap(MongoException.class, e -> new DatabaseConnectionException("Error de conexión con MongoDB"));

	}

	@Override
	public Mono<Person> update(Person entity) {
		// TODO Auto-generated method stub
		return repository.save(entity)
				 .onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));
	}

	@Override
	public Mono<Void> delete(Person entity) {
		// TODO Auto-generated method stub
		return repository.delete(entity)
	             .switchIfEmpty(Mono.error(new ResourceNotFoundException("Persona no encontrada con id: " + entity.getId())))
				 .onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));
	}

}
