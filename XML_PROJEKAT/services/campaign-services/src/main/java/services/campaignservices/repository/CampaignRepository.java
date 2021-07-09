package services.campaignservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import services.campaignservices.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign,Integer> {
    Campaign findOneById(int id);
}
