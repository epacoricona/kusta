package micro.desafio.kusta.application.service.impl;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mongodb.MongoException;

import micro.desafio.kusta.domain.entity.Person;
import micro.desafio.kusta.domain.entity.Person;
import micro.desafio.kusta.infrastructure.exception.DatabaseConnectionException;
import micro.desafio.kusta.infrastructure.repository.IPersonRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class PersonServiceImplTests {
	
	@Mock
	private IPersonRepository repository;
	
	@InjectMocks
	private PersonServiceImpl service;
	
    @Test
    public void testFindAllSuccess() {
        Person person1 = new Person("1", "esteban", "pacoricona");
        Person person2 = new Person("2", "oscar", "pacoricona");
        when(repository.findAll()).thenReturn(Flux.just(person1, person2));

        Flux<Person> result = service.findAll();

        StepVerifier.create(result)
                    .expectNext(person1)
                    .expectNext(person2)
                    .expectComplete()
                    .verify();
    }
    
    @Test
    public void testFindAllEmpty() {
        when(repository.findAll()).thenReturn(Flux.empty());

        Flux<Person> result = service.findAll();

        StepVerifier.create(result)
                    .expectComplete()
                    .verify();
    }
    

    @Test
    public void testFindByIdSuccess() {
        String id = "1";
        Person person = new Person(id, "esteban", "pacoricona");
        when(repository.findById(id)).thenReturn(Mono.just(person));

        Mono<Person> result = service.findById(id);

        StepVerifier.create(result)
                    .expectNext(person)
                    .expectComplete()
                    .verify();
    }
    
    @Test
    public void testSaveSuccess() {
        Person person = new Person("1", "esteban", "pacoricona");
        when(repository.save(person)).thenReturn(Mono.just(person));

        Mono<Person> result = service.save(person);

        StepVerifier.create(result)
                    .expectNext(person)
                    .expectComplete()
                    .verify();
    }
    
    @Test
    public void testSaveDatabaseConnectionException() {
        Person person = new Person("1", "esteban", "pacoricona");
        when(repository.save(person)).thenReturn(Mono.error(new MongoException("Error de conexión con MongoDB")));

        StepVerifier.create(service.save(person))
                    .expectError(DatabaseConnectionException.class)
                    .verify();
    }
    
    @Test
    public void testUpdateSuccess() {
        Person person = new Person("1", "esteban", "pacoricona");
        when(repository.save(person)).thenReturn(Mono.just(person));

        Mono<Person> result = service.update(person);

        StepVerifier.create(result)
                    .expectNext(person)
                    .expectComplete()
                    .verify();
    }

    @Test
    public void testUpdateDatabaseConnectionException() {
        Person person = new Person("1", "esteban", "pacoricona");
        when(repository.save(person)).thenReturn(Mono.error(new MongoException("Error de conexión con MongoDB")));

        StepVerifier.create(service.update(person))
                    .expectError(DatabaseConnectionException.class)
                    .verify();
    }
    
    @Test
    public void testDeleteSuccess() {
        Person person = new Person("1", "esteban", "pacoricona");
        when(repository.delete(person)).thenReturn(Mono.empty());

        Mono<Void> result = service.delete(person);

        StepVerifier.create(result)
                    .expectComplete()
                    .verify();
    }

    @Test
    public void testDeleteDatabaseConnectionException() {
        Person person = new Person("1", "esteban", "pacoricona");
        when(repository.delete(person)).thenReturn(Mono.error(new MongoException("Error de conexión con MongoDB")));

        StepVerifier.create(service.delete(person))
                    .expectError(DatabaseConnectionException.class)
                    .verify();
    }
    
    
}
