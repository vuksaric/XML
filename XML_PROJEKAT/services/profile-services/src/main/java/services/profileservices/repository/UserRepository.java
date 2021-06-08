package services.profileservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import services.profileservices.model.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    UserInfo save(UserInfo userInfo);
    UserInfo findOneById(int id);
    UserInfo findOneByUsername(String username);
}
