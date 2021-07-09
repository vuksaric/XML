package services.agentappservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import services.agentappservices.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findOneByUsername(String username);
    User findOneById(int id);
    User save(User user);
}
