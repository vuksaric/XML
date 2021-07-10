package services.postservices.service;

import org.springframework.web.multipart.MultipartFile;
import services.postservices.dto.CampaignRequest;
import services.postservices.dto.FeedStoryRequest;
import services.postservices.dto.PostResponse;
import services.postservices.dto.StoryResponse;

import java.io.IOException;
import java.util.List;

public interface IStoryService {
    int save(MultipartFile[] multipartFile, String location, String caption,boolean closeFriends, boolean highlight, String userInfoId, List<String> tags) throws IOException;
    List<StoryResponse> getForProfile(List<Integer> storyIds);
    List<StoryResponse> getHighlightForProfile(List<Integer> storyIds);
    List<StoryResponse> getStoriesFeed(List<FeedStoryRequest> requests);
    int newStoryCommercial(MultipartFile[] multipartFile, String caption, List<String> tags) throws IOException;
    List<StoryResponse> getStoryCommercials(List<CampaignRequest> requests);
}
