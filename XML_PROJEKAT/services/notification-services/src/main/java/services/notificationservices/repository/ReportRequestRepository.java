package services.notificationservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import services.notificationservices.model.ReportRequest;

@Repository
public interface ReportRequestRepository extends JpaRepository<ReportRequest,Integer> {
    ReportRequest findOneById(int id);
}
