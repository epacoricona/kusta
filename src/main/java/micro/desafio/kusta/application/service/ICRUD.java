package micro.desafio.kusta.application.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<Entity, Type> {
	Flux<Entity> findAll();
	Mono<Entity> findById(Type id);
	Mono<Entity> save(Entity entity);
	Mono<Entity> update(Entity entity);
	Mono<Void> delete(Entity entity);
}
