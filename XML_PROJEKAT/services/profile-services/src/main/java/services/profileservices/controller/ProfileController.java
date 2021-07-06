package services.profileservices.controller;

import org.springframework.web.bind.annotation.*;
import services.profileservices.dto.FavouriteRequest;
import services.profileservices.dto.ProfileDTO;
import services.profileservices.dto.ViewProfileDTO;
import services.profileservices.model.Profile;
import services.profileservices.service.IProfileService;
import services.profileservices.service.implementation.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final IProfileService profileService;

    public ProfileController(IProfileService profileService){this.profileService = profileService;}

    @PostMapping("/createProfile/{userInfoId}")
    public Boolean createProfile(@PathVariable int userInfoId){
        return profileService.createProfile(userInfoId);
    }

    @PutMapping("/addPost/{postId}/{userInfoId}")
    public Boolean addPost(@PathVariable int postId, @PathVariable int userInfoId){
        return profileService.addPost(postId,userInfoId);
    }
    @PutMapping("/addStory/{storyId}/{userInfoId}")
    public Boolean addStory(@PathVariable int storyId, @PathVariable int userInfoId){
        return profileService.addStory(storyId,userInfoId);
    }

    @GetMapping("/get/{username}")
    public ViewProfileDTO get(@PathVariable String username){
        return profileService.getProfileByUsername(username);
    }

    @GetMapping("/checkFollowing/{loggedInId}/{currentId}")
    public Boolean checkFollowing(@PathVariable int loggedInId, @PathVariable int currentId)
    {
        return profileService.checkFollowing(loggedInId, currentId);
    }

    @GetMapping("/checkBlocked/{loggedInId}/{currentId}")
    public Boolean checkBlocked(@PathVariable int loggedInId, @PathVariable int currentId)
    {
        return profileService.checkBlocked(loggedInId, currentId);
    }

    @GetMapping("/checkMuted/{loggedInId}/{currentId}")
    public Boolean checkMuted(@PathVariable int loggedInId, @PathVariable int currentId)
    {
        return profileService.checkMuted(loggedInId, currentId);
    }

    @PutMapping("/block/{loggedInId}/{currentId}")
    public void block(@PathVariable int loggedInId, @PathVariable int currentId){
        profileService.blockProfile(loggedInId, currentId);
    }

    @PutMapping("/mute/{loggedInId}/{currentId}")
    public void mute(@PathVariable int loggedInId, @PathVariable int currentId){
        profileService.muteProfile(loggedInId, currentId);
    }

    @PutMapping("/unmute/{loggedInId}/{currentId}")
    public void unmute(@PathVariable int loggedInId, @PathVariable int currentId){
        profileService.unmuteProfile(loggedInId, currentId);
    }

    @GetMapping("/getProfile/{userInfoId}")
    public ProfileDTO getProfile(@PathVariable int userInfoId){
        return profileService.getProfile(userInfoId);
    }

    @PutMapping("/editProfile")
    public Boolean editProfile(@RequestBody ProfileDTO profileDTO){
        return profileService.editProfile(profileDTO);
    }


    @PutMapping("/follow/{loggedInId}/{currentId}")
    public void follow(@PathVariable int loggedInId, @PathVariable int currentId){
         profileService.followProfile(loggedInId, currentId);
    }

    @PutMapping("/unfollow/{loggedInId}/{currentId}")
    public void unfollow(@PathVariable int loggedInId, @PathVariable int currentId){
        profileService.unfollowProfile(loggedInId, currentId);
    }

    @PutMapping("/acceptFollowRequest/{to}/{from}")
    public void acceptFollowRequest(@PathVariable int to, @PathVariable int from){
        profileService.acceptFollowRequest(to, from);
    }

    @PutMapping("/denyFollowRequest/{to}/{from}")
    public void denyFollowRequest(@PathVariable int to, @PathVariable int from){
        profileService.denyFollowRequest(to, from);
    }

    @GetMapping("/profilesForTagging/{userInfoId}")
    public List<String> getProfilesForTagging(@PathVariable int userInfoId){
        return profileService.getProfilesForTagging(userInfoId);
    }
    @GetMapping("/getPublicProfiles")
    public List<String> getPublicProfiles(){
        return profileService.getPublicProfiles();
    }

    @PostMapping("/findByUsername")
    public List<Integer> findByUsername(@RequestBody List<String> usernames){
        return profileService.findByUsername(usernames);
    }
    @GetMapping("/getPostIdsFeed/{userInfoId}")
    public List<Integer> getPostIdsFeed(@PathVariable int userInfoId){
        return profileService.getPostIdsFeed(userInfoId);
    }
    @PostMapping("/getTaggedUsernames")
    public List<String> getTaggedUsernames(@RequestBody List<Integer> taggedIds){
        return profileService.getTaggedUsernames(taggedIds);
    }

    @GetMapping("/getCollections/{profileId}")
    public List<String> getCollections(@PathVariable int profileId){
        return profileService.getCollections(profileId);
    }

    @GetMapping("/checkFavourite/{userInfoId}/{postId}")
    public Boolean checkFavourite(@PathVariable int userInfoId, @PathVariable int postId)
    {
        return profileService.checkFavourite(userInfoId, postId);
    }

    @PostMapping("/addFavourite")
    public void addFavourite(@RequestBody FavouriteRequest request){
         profileService.addFavourite(request);
    }
}
