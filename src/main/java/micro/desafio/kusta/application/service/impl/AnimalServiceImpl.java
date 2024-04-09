package micro.desafio.kusta.application.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import micro.desafio.kusta.application.service.IAnimalService;
import micro.desafio.kusta.domain.entity.Animal;
import micro.desafio.kusta.infrastructure.exception.DatabaseConnectionException;
import micro.desafio.kusta.infrastructure.exception.ResourceNotFoundException;
import micro.desafio.kusta.infrastructure.repository.IAnimalRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnimalServiceImpl implements IAnimalService {

	@Autowired
	private IAnimalRepository repository;
	
	@Override
	@CircuitBreaker(name= "kustaCB" , fallbackMethod = "fallBackFindAll")
	@Retry(name = "kustaCB")
	public Flux<Animal> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll()
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));
	}
	
	@Override
	@CircuitBreaker(name= "kustaCB" , fallbackMethod = "fallBackFindById")
	@Retry(name = "kustaCB")
	public Mono<Animal> findById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id)
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Animal no encontrado con id: " + id)))
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));

	}
	
	@Override	
	@CircuitBreaker(name= "kustaCB" , fallbackMethod = "fallBackSave")
	@Retry(name = "kustaCB")
	public Mono<Animal> save(Animal entity) {
		// TODO Auto-generated method stub
		return repository.save(entity)
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));
	}
	@Override
	@CircuitBreaker(name= "kustaCB" , fallbackMethod = "fallBackUpdate")
	@Retry(name = "kustaCB")
	public Mono<Animal> update(Animal entity) {
		// TODO Auto-generated method stub
		return repository.save(entity)
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));

	}
	@Override
	@CircuitBreaker(name= "kustaCB" , fallbackMethod = "fallBackDelete")
	@Retry(name = "kustaCB")
	public Mono<Void> delete(Animal entity) {
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
	
	private Mono<Void>  fallBackDelete() {
        return Mono.empty();
	}
}
