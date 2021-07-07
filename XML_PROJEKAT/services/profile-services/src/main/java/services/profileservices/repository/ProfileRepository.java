package services.profileservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import services.profileservices.model.Profile;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Integer> {
    Profile save(Profile profile);
    Profile findOneByUserInfoId(int id);
    Profile findOneById(int id);
    @Query("select p from Profile p where p.isPrivate = false and p.canBeTagged=true")
    List<Profile> findAllPublicAndCanBeTagged();
    @Query("select p from Profile p where p.isPrivate = false")
    List<Profile> findAllPublic();
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.profile_favourites WHERE favourite = ?1",nativeQuery = true)
    void removeFromFavourites(int id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.collection_posts WHERE post = ?1",nativeQuery = true)
    void removeFromCollection(int id);
}
