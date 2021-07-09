package services.campaignservices.service;

import services.campaignservices.dto.CampaignDTO;
import services.campaignservices.dto.CampaignRequest;
import services.campaignservices.dto.ProfileRequest;
import services.campaignservices.model.Campaign;

import java.util.List;

public interface ICampaignService {

    Campaign newCampaign(CampaignDTO campaignDTO);
    List<Campaign> getAll();
    void edit(Campaign campaign);
    void delete(Campaign campaign);
    List<CampaignRequest> getPostsForProfile(ProfileRequest profileRequest);
    List<CampaignRequest> getStoriesForProfile(ProfileRequest profileRequest);
}
