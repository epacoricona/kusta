package micro.desafio.kusta.infrastructure.handler;

import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import micro.desafio.kusta.application.service.IPersonService;
import micro.desafio.kusta.domain.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
@SpringBootTest
public class PersonHandlerTest {
	
	@Mock
	private IPersonService service;

	@InjectMocks
	private PersonHandler handler;

	@Test
	public void testFindAll() {
		when(service.findAll()).thenReturn(Flux.fromIterable(Arrays.asList(new Person(), new Person())));

		Mono<ServerResponse> responseMono = handler.findAll(Mockito.mock(ServerRequest.class));

		StepVerifier.create(responseMono).expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
				.verifyComplete();
	}
	
    @Test
    public void testFindByIdSuccess() {
        ServerRequest request = Mockito.mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("123");

        Person animal = new Person(); 
        when(service.findById("123")).thenReturn(Mono.just(animal));

        Mono<ServerResponse> responseMono = handler.findById(request);

        StepVerifier.create(responseMono)
                    .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                    .verifyComplete();
    }
    @Test
    public void testSaveSuccess() {
        ServerRequest request = Mockito.mock(ServerRequest.class);
        Person animal = new Person();
        when(request.bodyToMono(Person.class)).thenReturn(Mono.just(animal));
        when(service.save(animal)).thenReturn(Mono.just(animal));

        Mono<ServerResponse> responseMono = handler.save(request);

        StepVerifier.create(responseMono)
                    .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CREATED))
                    .verifyComplete();
    }
}
