package services.postservices.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import services.postservices.client.PictureVideoClient;
import services.postservices.client.ProfileClient;
import services.postservices.dto.ImageDTO;
import services.postservices.dto.PostResponse;
import services.postservices.dto.StoryResponse;
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
        LocalDateTime checkTime =  LocalDateTime.now().plusDays(1);
        for(Integer id : storyIds)
        {
            Story story = storyRepository.findOneById(id);
            if(story.getTimeStamp().isBefore(checkTime))
            {
                StoryResponse storyResponse = new StoryResponse(story);
                for(Integer idPicture : story.getPostInfo().getPictureIds())
                {
                    String src = pictureVideoClient.getLocationById(idPicture);
                    storyResponse.getContentSrcs().add(src);
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
                    String src = pictureVideoClient.getLocationById(idPicture);
                    storyResponse.getContentSrcs().add(src);
                }
                for(Integer idPicture : story.getPostInfo().getVideoIds())
                {
                    String src = pictureVideoClient.getLocationById(idPicture);
                    storyResponse.getContentSrcs().add(src);
                }
                result.add(storyResponse);
            }
        }

        return result;
    }
}
