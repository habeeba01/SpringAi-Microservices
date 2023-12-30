package Soring.AI.spring.ai.Repo;

import Soring.AI.spring.ai.Models.Infos;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InfoRepo extends MongoRepository<Infos,String> {
}
