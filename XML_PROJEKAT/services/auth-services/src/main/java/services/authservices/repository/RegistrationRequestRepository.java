package services.authservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import services.authservices.model.RegistrationRequest;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest,Integer> {
    RegistrationRequest findOneById(int id);
}
