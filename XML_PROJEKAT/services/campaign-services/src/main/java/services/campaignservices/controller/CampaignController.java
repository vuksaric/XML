package services.campaignservices.controller;

import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import services.campaignservices.dto.CampaignDTO;
import services.campaignservices.dto.CampaignRequest;
import services.campaignservices.dto.ProfileRequest;
import services.campaignservices.model.Campaign;
import services.campaignservices.service.ICampaignService;

import java.util.List;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private final ICampaignService campaignService;

    public CampaignController(ICampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/new")
    public Campaign newCampaign(@RequestBody CampaignDTO campaign)
    {
        return campaignService.newCampaign(campaign);
    }

    @GetMapping("/getAll")
    public List<Campaign> getAll()
    {
        return campaignService.getAll();
    }

    @PutMapping("/edit")
    public void edit(@RequestBody Campaign campaign)
    {
        campaignService.edit(campaign);
    }

    @PutMapping("/delete")
    public void delete(@RequestBody Campaign campaign)
    {
        campaignService.delete(campaign);
    }

    @PostMapping("/getPostsForProfile")
    public List<CampaignRequest> getPostsForProfile(@RequestBody ProfileRequest profileRequest)
    {
        return campaignService.getPostsForProfile(profileRequest);
    }

    @PostMapping("/getStoriesForProfile")
    public List<CampaignRequest> getStoriesForProfile(@RequestBody ProfileRequest profileRequest)
    {
        return campaignService.getStoriesForProfile(profileRequest);
    }
}
