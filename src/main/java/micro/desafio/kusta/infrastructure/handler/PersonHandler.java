package micro.desafio.kusta.infrastructure.handler;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.BodyInserters;

import micro.desafio.kusta.application.service.PersonService;
import micro.desafio.kusta.domain.entity.Person;
import micro.desafio.kusta.infrastructure.exception.DatabaseConnectionException;
import micro.desafio.kusta.infrastructure.exception.ResourceNotFoundException;
import reactor.core.publisher.Mono;

@Configuration
public class PersonHandler {

	@Autowired
	private PersonService service;

	public Mono<ServerResponse> findAll(ServerRequest req) {
		try {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll(), Person.class);
		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BodyInserters.fromValue(e.getMessage()));
		}
	}

	public Mono<ServerResponse> findById(ServerRequest req) {
		try {
			String id = req.pathVariable("id");
			return service.findById(id).flatMap(
					p -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(p)));
		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BodyInserters.fromValue(e.getMessage()));
		}
	}

	public Mono<ServerResponse> save(ServerRequest request) {
		try {
			Mono<Person> type = request.bodyToMono(Person.class);
			return type.flatMap(p -> service.save(p)).flatMap(
					flat -> ServerResponse.created(URI.create("api/type/".concat(String.valueOf(flat.getId()))))
							.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(flat)));
		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BodyInserters.fromValue(e.getMessage()));
		}
	}

	public Mono<ServerResponse> update(ServerRequest request) {
		try {
			Mono<Person> type = request.bodyToMono(Person.class);
			return type.flatMap(p -> service.update(p)).flatMap(
					flat -> ServerResponse.created(URI.create("api/type/".concat(String.valueOf(flat.getId()))))
							.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(flat)));
		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BodyInserters.fromValue(e.getMessage()));
		}
	}

	public Mono<ServerResponse> delete(ServerRequest request) {
		try {
			String id = request.pathVariable("id");
			return service.findById(id).flatMap(c -> {
				return service.delete(c).then(ServerResponse.noContent().build());
			}).switchIfEmpty(Mono.error(new ResourceNotFoundException("Person not found with id: " + id)));
		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BodyInserters.fromValue(e.getMessage()));
		}
	}
}
