package micro.desafio.kusta.infrastructure.handler;

import javax.swing.text.html.parser.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import micro.desafio.kusta.application.service.IDecisionService;
import reactor.core.publisher.Mono;

/**
 * Manejador para las entidades de decisión.
 */
@Configuration
public class DecisionEntityHandler {

	@Autowired
	private IDecisionService service;

	/**
	 * Maneja la solicitud para encontrar una entidad de decisión por su fuente.
	 *
	 * @param req La solicitud del servidor.
	 * @return La respuesta del servidor con la entidad de decisión encontrada.
	 */
	public Mono<ServerResponse> findDecisionEntity(ServerRequest req) {
		try {
			String source = req.pathVariable("source");
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(service.findDecision(source),
					Entity.class);
		} catch (Exception e) {
			return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(e.getMessage());
		}
	}
}