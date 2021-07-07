package services.postservices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="profile",url="${app.profile.url}")
public interface ProfileClient {
    @PutMapping("/profile/addPost/{postId}/{userInfoId}")
    Boolean addPost(@PathVariable int postId, @PathVariable int userInfoId);

    @PutMapping("/profile/addStory/{storyId}/{userInfoId}")
    Boolean addStory(@PathVariable int storyId, @PathVariable int userInfoId);

    @GetMapping("/profile/findByUsername")
    List<Integer> findByUsername(@RequestBody List<String> usernames);

    @PostMapping("/profile/getTaggedUsernames")
    List<String> getTaggedUsernames(@RequestBody List<Integer> taggedIds);

    @GetMapping("profile/getAccessiblePostIds/{userInfoId}")
   List<Integer> getAccessiblePostIds(@PathVariable int userInfoId );

}
