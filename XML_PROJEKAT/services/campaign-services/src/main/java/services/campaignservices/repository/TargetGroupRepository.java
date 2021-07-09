package services.campaignservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import services.campaignservices.model.TargetGroup;

public interface TargetGroupRepository extends JpaRepository<TargetGroup,Integer> {

    TargetGroup findOneById(int id);
}
