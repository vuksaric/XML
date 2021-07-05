package services.postservices.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
}
