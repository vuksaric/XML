package services.agentappservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import services.agentappservices.model.Image;

public interface ImageRepository extends JpaRepository<Image,Integer> {
    Image save(Image image);
    Image findOneById(int id);
}
