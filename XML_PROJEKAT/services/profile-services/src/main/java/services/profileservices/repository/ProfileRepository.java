package services.profileservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import services.profileservices.model.Profile;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile,Integer> {
    Profile save(Profile profile);
    Profile findOneByUserInfoId(int id);
    Profile findOneById(int id);
    @Query("select p from Profile p where p.isPrivate = false and p.canBeTagged=true")
    List<Profile> findAllPublicAndCanBeTagged();
    @Query("select p from Profile p where p.isPrivate = false")
    List<Profile> findAllPublic();

}
