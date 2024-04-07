package micro.desafio.kusta.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

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
	public Flux<Animal> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll().switchIfEmpty(Mono.error(new ResourceNotFoundException("Personas no encontradas")))
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));

	}

	@Override
	public Mono<Animal> findById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id)
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Animal no encontrado con id: " + id)))
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));

	}

	@Override
	public Mono<Animal> save(Animal entity) {
		// TODO Auto-generated method stub
		return repository.save(entity)
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));

	}

	@Override
	public Mono<Animal> update(Animal entity) {
		// TODO Auto-generated method stub
		return repository.save(entity)
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));

	}

	@Override
	public Mono<Void> delete(Animal entity) {
		// TODO Auto-generated method stub
		return repository.delete(entity)
				.switchIfEmpty(
						Mono.error(new ResourceNotFoundException("Animal no encontrado con id: " + entity.getId())))
				.onErrorMap(e -> new DatabaseConnectionException("Error de conexión con MongoDB"));

	}

}
