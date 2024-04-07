package micro.desafio.kusta.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import micro.desafio.kusta.domain.entity.Animal;
@Repository
public interface IAnimalRepository extends ReactiveMongoRepository<Animal, String> {

}
