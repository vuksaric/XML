package services.postservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import services.postservices.model.Story;

public interface StoryRepository extends JpaRepository<Story, Integer> {
    Story save(Story story);
    Story findOneById(int id);
}
