package services.postservices.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import services.postservices.client.PictureVideoClient;
import services.postservices.client.ProfileClient;
import services.postservices.dto.*;
import services.postservices.model.Post;
import services.postservices.model.PostInfo;
import services.postservices.model.Story;
import services.postservices.repository.StoryRepository;
import services.postservices.service.IStoryService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoryService implements IStoryService {

    private final StoryRepository storyRepository;
    private final PictureVideoClient pictureVideoClient;
    private final ProfileClient profileClient;

    public StoryService(StoryRepository storyRepository, PictureVideoClient pictureVideoClient, ProfileClient profileClient){
        this.storyRepository = storyRepository;
        this.pictureVideoClient = pictureVideoClient;
        this.profileClient = profileClient;
    }

    @Override
    public int save(MultipartFile[] multipartFile, String location, String caption, boolean closeFriends, boolean highlight, String userInfoId, List<String> tags) throws IOException {
        Story story = new Story();
        List<Integer> ids = new ArrayList<>();

        for(MultipartFile file : multipartFile){
            int contentId;
            if(file.getContentType().contains("image"))
                contentId = pictureVideoClient.uploadImage(new ImageDTO(file.getOriginalFilename(),file.getBytes(), true));
            else
                contentId = pictureVideoClient.uploadImage(new ImageDTO(file.getOriginalFilename(),file.getBytes(), false));
            ids.add(contentId);
        }
        List<Integer> taggedIds = profileClient.findByUsername(tags);
        PostInfo postInfo = new PostInfo();
        postInfo.setPictureIds(ids);
        postInfo.setCaption(caption);
        postInfo.setLocation(location);
        postInfo.setDate(LocalDate.now());
        postInfo.setTaggedIds(taggedIds);
        story.setPostInfo(postInfo);
        story.setHighlight(highlight);
        story.setCloseFriends(closeFriends);
        story.setTimeStamp(LocalDateTime.now());

        Story new_story = storyRepository.save(story);
        int userId = Integer.parseInt(userInfoId);
        profileClient.addStory(new_story.getId(), userId);
        return new_story.getId();
    }

    @Override
    public List<StoryResponse> getForProfile(List<Integer> storyIds) {
        List<StoryResponse> result = new ArrayList<>();
        for(Integer id : storyIds)
        {
            Story story = storyRepository.findOneById(id);
            LocalDateTime checkTime =  story.getTimeStamp().plusDays(1);
            if(checkTime.isAfter(LocalDateTime.now()))
            {
                StoryResponse storyResponse = new StoryResponse(story);
                for(Integer idPicture : story.getPostInfo().getPictureIds())
                {
                    boolean image = pictureVideoClient.getImageById(idPicture);
                    String src = pictureVideoClient.getLocationById(idPicture);
                    PictureDTO pictureDTO = new PictureDTO(src,image);
                    storyResponse.getContent().add(pictureDTO);
                }
                result.add(storyResponse);
            }
        }

        return result;
    }

    @Override
    public List<StoryResponse> getHighlightForProfile(List<Integer> storyIds) {
        List<StoryResponse> result = new ArrayList<>();
        for(Integer id : storyIds)
        {
            Story story = storyRepository.findOneById(id);
            if(story.getHighlight())
            {
                StoryResponse storyResponse = new StoryResponse(story);
                for(Integer idPicture : story.getPostInfo().getPictureIds())
                {
                    boolean image = pictureVideoClient.getImageById(idPicture);
                    String src = pictureVideoClient.getLocationById(idPicture);
                    PictureDTO pictureDTO = new PictureDTO(src,image);
                    storyResponse.getContent().add(pictureDTO);
                }
                result.add(storyResponse);
            }
        }

        return result;
    }

    @Override
    public List<StoryResponse> getStoriesFeed(List<FeedStoryRequest> requests) {
        List<StoryResponse> result = new ArrayList<>();
        for(FeedStoryRequest request : requests)
        {
            for(Integer storyId : request.getStoryIds()) {
                Story story = storyRepository.findOneById(storyId);
                LocalDateTime checkTime = story.getTimeStamp().plusDays(1);
                if (checkTime.isAfter(LocalDateTime.now())) {
                    StoryResponse storyResponse = new StoryResponse(story);
                    for (Integer idPicture : story.getPostInfo().getPictureIds()) {
                        boolean image = pictureVideoClient.getImageById(idPicture);
                        String src = pictureVideoClient.getLocationById(idPicture);
                        PictureDTO pictureDTO = new PictureDTO(src, image);
                        storyResponse.getContent().add(pictureDTO);
                    }
                    storyResponse.setUsername(request.getUsername());
                    result.add(storyResponse);
                }
            }
        }

        return result;
    }

    @Override
    public int newStoryCommercial(MultipartFile[] multipartFile, String caption, List<String> tags) throws IOException {
        Story story = new Story();
        List<Integer> ids = new ArrayList<>();

        for(MultipartFile file : multipartFile){
            int contentId;
            if(file.getContentType().contains("image"))
                contentId = pictureVideoClient.uploadImage(new ImageDTO(file.getOriginalFilename(),file.getBytes(), true));
            else
                contentId = pictureVideoClient.uploadImage(new ImageDTO(file.getOriginalFilename(),file.getBytes(), false));
            ids.add(contentId);
        }
        List<Integer> taggedIds = profileClient.findByUsername(tags);
        PostInfo postInfo = new PostInfo();
        postInfo.setPictureIds(ids);
        postInfo.setCaption(caption);
        postInfo.setDate(LocalDate.now());
        postInfo.setTaggedIds(taggedIds);
        story.setPostInfo(postInfo);
        story.setHighlight(false);
        story.setCloseFriends(false);
        story.setTimeStamp(LocalDateTime.now());

        Story new_story = storyRepository.save(story);
        return new_story.getId();
    }

    @Override
    public List<StoryResponse> getStoryCommercials(List<CampaignRequest> requests) {
        List<StoryResponse> result = new ArrayList<>();
        for(CampaignRequest request : requests)
        {
            Story story = storyRepository.findOneById(request.getPostId());
            StoryResponse storyResponse = new StoryResponse(story);
            storyResponse.setUsername(request.getUsername());
            for(Integer idPicture : story.getPostInfo().getPictureIds())
            {
                boolean image = pictureVideoClient.getImageById(idPicture);
                String src = pictureVideoClient.getLocationById(idPicture);
                PictureDTO pictureDTO = new PictureDTO(src,image);
                storyResponse.getContent().add(pictureDTO);
            }
            storyResponse.setCommercial(true);
            storyResponse.setWebsite(request.getWebsite());
            result.add(storyResponse);
        }

        return result;
    }
}
