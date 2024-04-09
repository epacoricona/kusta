package micro.desafio.kusta.application.service.impl;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mongodb.MongoException;

import micro.desafio.kusta.domain.entity.Animal;
import micro.desafio.kusta.infrastructure.exception.DatabaseConnectionException;
import micro.desafio.kusta.infrastructure.exception.ResourceNotFoundException;
import micro.desafio.kusta.infrastructure.repository.IAnimalRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
@SpringBootTest
public class AnimalServiceImplTest {
    @Mock
    private IAnimalRepository repository;
    
    @InjectMocks
    private AnimalServiceImpl service;
    
    @Test
    public void testFindAllSuccess() {
        Animal animal1 = new Animal("1", "perro", "canido");
        Animal animal2 = new Animal("2", "gato", "felino");
        when(repository.findAll()).thenReturn(Flux.just(animal1, animal2));

        Flux<Animal> result = service.findAll();

        StepVerifier.create(result)
                    .expectNext(animal1)
                    .expectNext(animal2)
                    .expectComplete()
                    .verify();
    }
    
    @Test
    public void testFindAllEmpty() {
        when(repository.findAll()).thenReturn(Flux.empty());

        Flux<Animal> result = service.findAll();

        StepVerifier.create(result)
                    .expectComplete()
                    .verify();
    }

    @Test
    public void testFindByIdSuccess() {
        String id = "1";
        Animal animal = new Animal(id, "Dog", "felino");
        when(repository.findById(id)).thenReturn(Mono.just(animal));

        Mono<Animal> result = service.findById(id);

        StepVerifier.create(result)
                    .expectNext(animal)
                    .expectComplete()
                    .verify();
    }
    
    @Test
    public void testSaveSuccess() {
        Animal animal = new Animal("1", "perro", "canido");
        when(repository.save(animal)).thenReturn(Mono.just(animal));

        Mono<Animal> result = service.save(animal);

        StepVerifier.create(result)
                    .expectNext(animal)
                    .expectComplete()
                    .verify();
    }
    
    @Test
    public void testSaveDatabaseConnectionException() {
        Animal animal = new Animal("1", "perro", "canido");
        when(repository.save(animal)).thenReturn(Mono.error(new MongoException("Error de conexión con MongoDB")));

        StepVerifier.create(service.save(animal))
                    .expectError(DatabaseConnectionException.class)
                    .verify();
    }
    
    @Test
    public void testUpdateSuccess() {
        Animal animal = new Animal("1", "perro", "canido");
        when(repository.save(animal)).thenReturn(Mono.just(animal));

        Mono<Animal> result = service.update(animal);

        StepVerifier.create(result)
                    .expectNext(animal)
                    .expectComplete()
                    .verify();
    }

    @Test
    public void testUpdateDatabaseConnectionException() {
        Animal animal = new Animal("1", "perro", "canido");
        when(repository.save(animal)).thenReturn(Mono.error(new MongoException("Error de conexión con MongoDB")));

        StepVerifier.create(service.update(animal))
                    .expectError(DatabaseConnectionException.class)
                    .verify();
    }
    
    @Test
    public void testDeleteSuccess() {
        Animal animal = new Animal("1", "perro", "canido");
        when(repository.delete(animal)).thenReturn(Mono.empty());

        Mono<Void> result = service.delete(animal);

        StepVerifier.create(result)
                    .expectComplete()
                    .verify();
    }

    @Test
    public void testDeleteDatabaseConnectionException() {
        Animal animal = new Animal("1", "perro", "canido");
        when(repository.delete(animal)).thenReturn(Mono.error(new MongoException("Error de conexión con MongoDB")));

        StepVerifier.create(service.delete(animal))
                    .expectError(DatabaseConnectionException.class)
                    .verify();
    }
    
    
}
