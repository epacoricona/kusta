package micro.desafio.kusta.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import micro.desafio.kusta.domain.entity.Person;
@Repository
public interface IPersonRepository extends ReactiveMongoRepository<Person, String> {

}
