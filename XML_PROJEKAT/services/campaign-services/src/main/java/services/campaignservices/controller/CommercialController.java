package services.campaignservices.controller;

import org.springframework.web.bind.annotation.*;
import services.campaignservices.dto.CommercialDTO;
import services.campaignservices.model.Campaign;
import services.campaignservices.service.ICommercialService;

import java.io.IOException;

@RestController
@RequestMapping("/commercial")
public class CommercialController {

    private final ICommercialService commercialService;

    public CommercialController(ICommercialService commercialService) {
        this.commercialService = commercialService;
    }

    @PostMapping("/new")
    public int newCommercial(@RequestBody CommercialDTO request) throws IOException {
        return commercialService.newCommercial(request.getPostId(),request.getWebsite());
    }
}
