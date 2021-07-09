package services.campaignservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import services.campaignservices.model.Commercial;

public interface CommercialRepository extends JpaRepository<Commercial,Integer> {

    Commercial findOneById(int id);
}
