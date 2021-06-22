package services.notificationservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import services.notificationservices.model.FollowRequest;

import java.util.List;

public interface FollowRequestRepository extends JpaRepository<FollowRequest,Integer> {

    @Query("select fr from FollowRequest fr where fr.toProfileId = ?1")
    List<FollowRequest> findAllByToProfileId(int id);
    @Query("select fr from FollowRequest fr where fr.toProfileId = ?1 and fr.fromProfileId = ?2")
    FollowRequest findOneByToAndFrom(int to, int from);
}
