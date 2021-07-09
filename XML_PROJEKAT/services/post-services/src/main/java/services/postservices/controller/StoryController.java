package services.postservices.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.postservices.dto.*;
import services.postservices.service.IStoryService;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {
    private final IStoryService storyService;
    public StoryController(IStoryService storyService){this.storyService = storyService;}

    @PostMapping("/create")
    public Integer createStory(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("location") String location, @RequestParam("caption") String caption,
                               @RequestParam("close_friends") boolean closeFriends, @RequestParam("highlight") boolean highlight,
                               @RequestParam("userInfoId") String userInfoId,  @RequestParam("tags") List<String> tags) throws Exception{
        return storyService.save(multipartFile, location, caption, closeFriends,highlight, userInfoId, tags);
    }

    @PostMapping("/getStories")
    public List<StoryResponse> getStoryByStoryIds(@RequestBody ProfilePostRequest profilePostRequest){
        return storyService.getForProfile(profilePostRequest.getPostIds());
    }

    @PostMapping("/getHighlights")
    public List<StoryResponse> getHighlightByStoryIds(@RequestBody ProfilePostRequest profilePostRequest){
        return storyService.getHighlightForProfile(profilePostRequest.getPostIds());
    }

    @PostMapping("/getStoriesFeed")
    public List<StoryResponse> getStoriesFeed(@RequestBody List<FeedStoryRequest> requests) {
        return storyService.getStoriesFeed(requests);
    }

    @PostMapping("/newStoryCommercial")
    public Integer newStoryCommercial(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("caption") String caption, @RequestParam("tags") List<String> tags) throws Exception{
        return storyService.newStoryCommercial(multipartFile, caption,tags);
    }

    @PostMapping("/getStoryCommercials")
    public List<StoryResponse> getStoryCommercials(@RequestBody List<CampaignRequest> requests){
        return storyService.getStoryCommercials(requests);

    }

}
