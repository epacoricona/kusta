package micro.desafio.kusta.application.service;

import reactor.core.publisher.Flux;

public interface IDecisionService {
	public Flux<?> findDecision(String source);
}
