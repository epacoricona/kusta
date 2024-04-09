package micro.desafio.kusta.infrastructure.handler;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import micro.desafio.kusta.application.service.IAnimalService;
import micro.desafio.kusta.domain.entity.Animal;
import reactor.core.publisher.Mono;

/**
 * Manejador para las operaciones relacionadas con los animales.
 */
@Configuration
public class AnimalHandler {

	@Autowired
	private IAnimalService service;

	/**
	 * Maneja la solicitud para encontrar todos los animales.
	 *
	 * @param req La solicitud del servidor.
	 * @return La respuesta del servidor con la lista de animales encontrados.
	 */
	public Mono<ServerResponse> findAll(ServerRequest req) {
		try {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll(), Animal.class);
		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BodyInserters.fromValue(e.getMessage()));
		}
	}

	/**
	 * Maneja la solicitud para encontrar un animal por su ID.
	 *
	 * @param req La solicitud del servidor.
	 * @return La respuesta del servidor con el animal encontrado.
	 */
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

	/**
	 * Maneja la solicitud para guardar un nuevo animal.
	 *
	 * @param request La solicitud del servidor.
	 * @return La respuesta del servidor con el animal guardado.
	 */
	public Mono<ServerResponse> save(ServerRequest request) {
		try {
			Mono<Animal> type = request.bodyToMono(Animal.class);
			return type.flatMap(p -> service.save(p))
					.flatMap(flat -> ServerResponse
							.created(URI.create("api/animal/".concat(String.valueOf(flat.getId()))))
							.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(flat)));
		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BodyInserters.fromValue(e.getMessage()));
		}
	}

	/**
	 * Maneja la solicitud para actualizar un animal existente.
	 *
	 * @param request La solicitud del servidor.
	 * @return La respuesta del servidor con el animal actualizado.
	 */
	public Mono<ServerResponse> update(ServerRequest request) {
		try {
			Mono<Animal> type = request.bodyToMono(Animal.class);
			return type.flatMap(p -> service.update(p))
					.flatMap(flat -> ServerResponse
							.created(URI.create("api/animal/".concat(String.valueOf(flat.getId()))))
							.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(flat)));
		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BodyInserters.fromValue(e.getMessage()));
		}
	}

	/**
	 * Maneja la solicitud para eliminar un animal por su ID.
	 *
	 * @param request La solicitud del servidor.
	 * @return La respuesta del servidor con el estado de la eliminación.
	 */
	public Mono<ServerResponse> delete(ServerRequest request) {
		try {
			String id = request.pathVariable("id");
			return service.findById(id).flatMap(c -> {
				return service.delete(c).then(ServerResponse.noContent().build());
			}).switchIfEmpty(ServerResponse.notFound().build());
		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BodyInserters.fromValue(e.getMessage()));
		}
	}

}