package services.campaignservices.service.implementation;

import org.springframework.stereotype.Service;
import services.campaignservices.dto.CampaignDTO;
import services.campaignservices.dto.CampaignRequest;
import services.campaignservices.dto.ProfileRequest;
import services.campaignservices.model.Campaign;
import services.campaignservices.model.Commercial;
import services.campaignservices.model.TargetGroup;
import services.campaignservices.repository.CampaignRepository;
import services.campaignservices.service.ICampaignService;
import services.campaignservices.service.ITargetGroupService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService implements ICampaignService {

    private final CampaignRepository campaignRepository;
    private final CommercialService commercialService;
    private final ITargetGroupService targetGroupService;

    public CampaignService(CampaignRepository campaignRepository, CommercialService commercialService, ITargetGroupService targetGroupService) {
        this.campaignRepository = campaignRepository;
        this.commercialService = commercialService;
        this.targetGroupService = targetGroupService;
    }

    @Override
    public Campaign newCampaign(CampaignDTO campaignDTO)
    {
        Campaign campaign = new Campaign();
        campaign.setChangedDate(campaignDTO.getChangedDate());
        campaign.setCountAllowed(campaignDTO.getCountAllowed());
        campaign.setCountDone(campaignDTO.getCountDone());
        campaign.setStartDate(campaignDTO.getStartDate());
        campaign.setEndDate(campaignDTO.getEndDate());
        campaign.setIsItPost(campaignDTO.getIsItPost());
        campaign.setTargetGroup(campaignDTO.getTargetGroup());
        campaign.setCommercials(new ArrayList<>());
        campaign.setUsername(campaignDTO.getUsername());
        for(Integer id : campaignDTO.getCommercials())
        {
            campaign.getCommercials().add(commercialService.getById(id));
        }
        return campaignRepository.save(campaign);
    }

    @Override
    public List<Campaign> getAll() {
        return campaignRepository.findAll();
    }

    @Override
    public void edit(Campaign campaign) {
        Campaign current = campaignRepository.findOneById(campaign.getId());
        TargetGroup targetGroup = targetGroupService.getById(current.getTargetGroup().getId());
        current.setChangedDate(LocalDate.now());
        current.setCountAllowed(campaign.getCountAllowed());
        current.setStartDate(campaign.getStartDate());
        current.setEndDate(campaign.getEndDate());
        current.setIsItPost(campaign.getIsItPost());
        targetGroup.setGender(campaign.getTargetGroup().getGender());
        targetGroup.setEndAge(campaign.getTargetGroup().getEndAge());
        targetGroup.setStartAge(campaign.getTargetGroup().getStartAge());
        targetGroup.setProfileCategory(campaign.getTargetGroup().getProfileCategory());
        current.setTargetGroup(targetGroup);
        campaignRepository.save(current);
    }

    @Override
    public void delete(Campaign campaign) {
        Campaign current = campaignRepository.findOneById(campaign.getId());
        campaignRepository.delete(current);
    }

    @Override
    public List<CampaignRequest> getPostsForProfile(ProfileRequest profileRequest) {
        List<Campaign> campaigns = campaignRepository.findAll();
        LocalDate today = LocalDate.now();
        int age = today.getYear() - profileRequest.getDateOfBirth().getYear();
        List<CampaignRequest> result = new ArrayList<>();
        for(Campaign campaign : campaigns)
        {
            if(campaign.getIsItPost() == true && campaign.getTargetGroup().getStartAge() <= age && age <= campaign.getTargetGroup().getEndAge() &&
                    campaign.getTargetGroup().getGender() == profileRequest.getGender()
                    && campaign.getCountAllowed() > campaign.getCountDone())
            {
                if(campaign.getEndDate() != null)
                {
                    if((today.isBefore(campaign.getEndDate()) && today.isAfter(campaign.getStartDate())) || today.isEqual(campaign.getStartDate()) || today.isEqual(campaign.getEndDate()))
                    {
                        campaign.setCountDone(campaign.getCountDone() + 1);
                        campaignRepository.save(campaign);
                        for(Commercial commercial : campaign.getCommercials())
                        {
                            result.add(new CampaignRequest(commercial.getPostId(),commercial.getLink(),campaign.getUsername()));
                        }
                    }
                }
                else
                {
                    if(today.isAfter(campaign.getStartDate()) || today.isEqual(campaign.getStartDate()))
                    {
                        campaign.setCountDone(campaign.getCountDone() + 1);
                        campaignRepository.save(campaign);
                        for(Commercial commercial : campaign.getCommercials())
                        {
                            result.add(new CampaignRequest(commercial.getPostId(),commercial.getLink(),campaign.getUsername()));
                        }
                    }
                }

            }
        }

        return result;
    }

    @Override
    public List<CampaignRequest> getStoriesForProfile(ProfileRequest profileRequest) {
        List<Campaign> campaigns = campaignRepository.findAll();
        LocalDate today = LocalDate.now();
        int age = today.getYear() - profileRequest.getDateOfBirth().getYear();
        List<CampaignRequest> result = new ArrayList<>();
        for(Campaign campaign : campaigns)
        {
            if(campaign.getIsItPost() == false && campaign.getTargetGroup().getStartAge() <= age && age <= campaign.getTargetGroup().getEndAge() &&
                    campaign.getTargetGroup().getGender() == profileRequest.getGender()
                    && campaign.getCountAllowed() > campaign.getCountDone())
            {
                if(campaign.getEndDate() != null)
                {
                    if ((today.isBefore(campaign.getEndDate()) && today.isAfter(campaign.getStartDate())) || today.isEqual(campaign.getStartDate()) || today.isEqual(campaign.getEndDate())) {
                        campaign.setCountDone(campaign.getCountDone() + 1);
                        campaignRepository.save(campaign);
                        for (Commercial commercial : campaign.getCommercials()) {
                            result.add(new CampaignRequest(commercial.getPostId(), commercial.getLink(), campaign.getUsername()));
                        }
                    }
                }
                else
                {
                    if(today.isAfter(campaign.getStartDate()) || today.isEqual(campaign.getStartDate()))
                    {
                        campaign.setCountDone(campaign.getCountDone() + 1);
                        campaignRepository.save(campaign);
                        for(Commercial commercial : campaign.getCommercials())
                        {
                            result.add(new CampaignRequest(commercial.getPostId(),commercial.getLink(),campaign.getUsername()));
                        }
                    }
                }
            }
        }

        return result;
    }
}
