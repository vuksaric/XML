package services.notificationservices.controller;

import org.springframework.web.bind.annotation.*;
import services.notificationservices.model.FollowRequest;
import services.notificationservices.model.ReportRequest;
import services.notificationservices.service.IReportRequestService;

import java.util.List;

@RestController
@RequestMapping("/reportRequest")
public class ReportRequestController {

    private final IReportRequestService reportRequestService;

    public ReportRequestController(IReportRequestService reportRequestService) {
        this.reportRequestService = reportRequestService;
    }

    @GetMapping("/getAll")
    public List<ReportRequest> getAll()
    {
        return reportRequestService.getAll();
    }

    @PostMapping("/save/{postId}/{username}")
    public void save(@PathVariable int postId, @PathVariable String username)
    {
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setPostId(postId);
        reportRequest.setUsername(username);
        reportRequestService.save(reportRequest);
    }

    @PutMapping("/process/{id}")
    public void process(@PathVariable int id)
    {
        reportRequestService.process(id);
    }
}
