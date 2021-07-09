package services.notificationservices.service.implementation;

import org.springframework.stereotype.Service;
import services.notificationservices.model.ReportRequest;
import services.notificationservices.repository.ReportRequestRepository;
import services.notificationservices.service.IReportRequestService;

import java.util.List;

@Service
public class ReportRequestService implements IReportRequestService {

    private final ReportRequestRepository reportRequestRepository;

    public ReportRequestService(ReportRequestRepository reportRequestRepository) {
        this.reportRequestRepository = reportRequestRepository;
    }


    @Override
    public List<ReportRequest> getAll() {
        return reportRequestRepository.findAll();
    }

    @Override
    public void save(ReportRequest reportRequest) {
        reportRequestRepository.save(reportRequest);
    }

    @Override
    public void process(int id) {
        ReportRequest reportRequest = reportRequestRepository.findOneById(id);
        reportRequest.setProcessed(true);
        reportRequestRepository.save(reportRequest);
    }
}
