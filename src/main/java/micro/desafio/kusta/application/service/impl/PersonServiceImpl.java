package micro.desafio.kusta.application.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mongodb.MongoException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import micro.desafio.kusta.application.service.IPersonService;
import micro.desafio.kusta.domain.entity.Animal;
import micro.desafio.kusta.domain.entity.Person;
import micro.desafio.kusta.infrastructure.exception.DatabaseConnectionException;
import micro.desafio.kusta.infrastructure.exception.ResourceNotFoundException;
import micro.desafio.kusta.infrastructure.repository.IPersonRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements IPersonService {

	@Autowired
	private IPersonRepository repository;

	@Override
	@CircuitBreaker(name = "kustaCB", fallbackMethod = "fallBackFindAll")
	@Retry(name = "kustaCB")
	public Flux<Person> findAll() {
		return repository.findAll()
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));

	}

	@Override
	@CircuitBreaker(name = "kustaCB", fallbackMethod = "fallBackFindById")
	@Retry(name = "kustaCB")
	public Mono<Person> findById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id)
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Persona no encontrada con id: " + id)))
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));
	}

	@Override
	@CircuitBreaker(name = "kustaCB", fallbackMethod = "fallBackSave")
	@Retry(name = "kustaCB")
	public Mono<Person> save(Person entity) {
		// TODO Auto-generated method stub
		entity.setId(UUID.randomUUID().toString());
		return repository.save(entity).onErrorMap(MongoException.class,
				e -> new DatabaseConnectionException("Error de conexión con MongoDB"));

	}

	@Override
	@CircuitBreaker(name = "kustaCB", fallbackMethod = "fallBackUpdate")
	@Retry(name = "kustaCB")
	public Mono<Person> update(Person entity) {
		// TODO Auto-generated method stub
		return repository.save(entity)
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));
	}

	@Override
	@CircuitBreaker(name = "kustaCB", fallbackMethod = "fallBackDelete")
	@Retry(name = "kustaCB")
	public Mono<Void> delete(Person entity) {
		// TODO Auto-generated method stub
		return repository.delete(entity)
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));
	}

	private Flux<Animal> fallBackFindAll() {
		return Flux.empty();
	}

	private Mono<Animal> fallBackFindById() {
		return Mono.empty();
	}

	private Mono<Animal> fallBackSave() {
		return Mono.empty();
	}

	private Mono<Animal> fallBackUpdate() {
		return Mono.empty();
	}

	private Mono<Void> fallBackDelete() {
		return Mono.empty();
	}
}
