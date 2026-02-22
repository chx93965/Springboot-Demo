package SpringbootDemo.repository;

import SpringbootDemo.entity.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public interface MessageRepo extends CrudRepository<Message, Long> {
}
