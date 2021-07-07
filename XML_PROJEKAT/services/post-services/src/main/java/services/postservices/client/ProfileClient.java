package services.postservices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="profile",url="${app.profile.url}")
public interface ProfileClient {
    @PutMapping("/profile/addPost/{postId}/{userInfoId}")
    Boolean addPost(@PathVariable int postId, @PathVariable int userInfoId);

    @PutMapping("/profile/addStory/{storyId}/{userInfoId}")
    Boolean addStory(@PathVariable int storyId, @PathVariable int userInfoId);

    @GetMapping("/profile/findByUsername")
    List<Integer> findByUsername(@RequestBody List<String> usernames);

    @GetMapping("profile/getAccessiblePostIds/{userInfoId}")
   List<Integer> getAccessiblePostIds(@PathVariable int userInfoId );
}
