package micro.desafio.kusta.application.service.impl;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import micro.desafio.kusta.application.service.IDecisionService;
import micro.desafio.kusta.domain.entity.DataSourceRepository;
import micro.desafio.kusta.infrastructure.repository.IAnimalRepository;
import micro.desafio.kusta.infrastructure.repository.IPersonRepository;
import reactor.core.publisher.Flux;
@Service
public class DecisionServiceImpl implements IDecisionService{

	@Autowired
	private IAnimalRepository animalRepository;
	
	@Autowired
	private IPersonRepository personaRepository;
	
	@Autowired
	private KieSession session;
	
	@Override
	public Flux<?> findDecision(String source) {
		DataSourceRepository dataSourceRepository=new DataSourceRepository();	
		dataSourceRepository.setDataSource(source);
		
		session.insert(dataSourceRepository);		
		session.fireAllRules();

        if ("RepositoryPersona".equalsIgnoreCase(dataSourceRepository.getRepository())) {
            return personaRepository.findAll();
        } else if ("RepositoryAnimal".equalsIgnoreCase(dataSourceRepository.getRepository())) {
            return animalRepository.findAll();
        } else {
            return Flux.error(new IllegalArgumentException("Fuente de datos no válida"));
        }
	}
}
