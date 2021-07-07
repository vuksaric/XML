package services.notificationservices.service;

import services.notificationservices.model.ReportRequest;

import java.util.List;

public interface IReportRequestService {

    List<ReportRequest> getAll();
    void save(ReportRequest reportRequest);
    void process(int id);
}
