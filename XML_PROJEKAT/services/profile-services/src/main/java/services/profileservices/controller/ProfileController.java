package services.profileservices.controller;

import org.springframework.web.bind.annotation.*;
import services.profileservices.dto.ProfileDTO;
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

    @GetMapping("/get/{userInfoId}")
    public Profile get(@PathVariable int userInfoId){
        return profileService.getByUserInfoId(userInfoId);
    }

    @GetMapping("/checkFollowing/{loggedInId}/{currentId}")
    public Boolean checkFollowing(@PathVariable int loggedInId, @PathVariable int currentId)
    {
        return profileService.checkFollowing(loggedInId, currentId);
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

    @GetMapping("/getPostIdsFeed/{userInfoId}")
    public List<Integer> getPostIdsFeed(@PathVariable int userInfoId){
        return profileService.getPostIdsFeed(userInfoId);
    }

}
