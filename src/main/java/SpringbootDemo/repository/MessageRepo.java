package SpringbootDemo.repository;

import SpringbootDemo.entity.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Scope("singleton")
public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderByIdAsc();
}
