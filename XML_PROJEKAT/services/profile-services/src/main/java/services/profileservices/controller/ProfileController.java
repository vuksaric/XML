package services.profileservices.controller;

import org.springframework.web.bind.annotation.*;
import services.profileservices.dto.FavouriteRequest;
import services.profileservices.dto.FavouriteResponse;
import services.profileservices.dto.ProfileDTO;
import services.profileservices.dto.ProfileSettingsDTO;
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

    @GetMapping("/checkCloseFriends/{loggedInId}/{currentId}")
    public Boolean checkCloseFriends(@PathVariable int loggedInId, @PathVariable int currentId)
    {
        return profileService.checkCloseFriends(loggedInId, currentId);
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

    @GetMapping("/getCloseFriends/{userInfoId}")
    public List<String> getCloseFriends(@PathVariable int userInfoId ){
        return profileService.getCloseFriends(userInfoId);
    }
    @GetMapping("/getProfilesForCloseFriends/{userInfoId}")
    public List<String> getProfilesForCloseFriends(@PathVariable int userInfoId ){
        return profileService.getProfilesForCloseFriends(userInfoId);
    }

    @GetMapping("/getFollowingProfiles/{userInfoId}")
    public List<String> getFollowingProfiles(@PathVariable int userInfoId ){
        return profileService.getFollowingProfiles(userInfoId);
    }
    @GetMapping("/getProfilesForSettings/{userInfoId}")
    public List<ProfileSettingsDTO> getProfilesForSettings(@PathVariable int userInfoId ){
        return profileService.getProfilesForSettings(userInfoId);
    }
    @PutMapping("/mutePost/{loggedInId}/{currentId}")
    public void mutePost(@PathVariable int loggedInId, @PathVariable String currentId){
        profileService.mutePost(loggedInId, currentId);
    }

    @PutMapping("/unmutePost/{loggedInId}/{currentId}")
    public void unmutePost(@PathVariable int loggedInId, @PathVariable String currentId){
        profileService.unmutePost(loggedInId, currentId);
    }
    @PutMapping("/muteStory/{loggedInId}/{currentId}")
    public void muteStory(@PathVariable int loggedInId, @PathVariable String currentId){
        profileService.muteStory(loggedInId, currentId);
    }

    @PutMapping("/unmuteStory/{loggedInId}/{currentId}")
    public void unmuteStory(@PathVariable int loggedInId, @PathVariable String currentId){
        profileService.unmuteStory(loggedInId, currentId);
    }
    @PutMapping("/muteComment/{loggedInId}/{currentId}")
    public void muteComment(@PathVariable int loggedInId, @PathVariable String currentId){
        profileService.muteComment(loggedInId, currentId);
    }

    @PutMapping("/unmuteComment/{loggedInId}/{currentId}")
    public void unmuteComment(@PathVariable int loggedInId, @PathVariable String currentId){
        profileService.unmuteComment(loggedInId, currentId);
    }
    @PutMapping("/muteMessage/{loggedInId}/{currentId}")
    public void muteMessage(@PathVariable int loggedInId, @PathVariable String currentId){
        profileService.muteMessage(loggedInId, currentId);
    }

    @PutMapping("/unmuteMessage/{loggedInId}/{currentId}")
    public void unmuteMessage(@PathVariable int loggedInId, @PathVariable String currentId){
        profileService.unmuteMessage(loggedInId, currentId);
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

    @GetMapping("/getFavourites/{profileId}")
    public FavouriteResponse getFavourites(@PathVariable int profileId) {
        return profileService.getFavourites(profileId);
    }

    @PutMapping("/addCloseFriend/{loggedInId}/{closeFriend}")
    public void addCloseFriend(@PathVariable int loggedInId, @PathVariable String closeFriend){
        profileService.addCloseFriend(loggedInId, closeFriend);
    }
    @PutMapping("/removeCloseFriend/{loggedInId}/{closeFriend}")
    public void removeCloseFriend(@PathVariable int loggedInId, @PathVariable String closeFriend){
        profileService.removeCloseFriend(loggedInId, closeFriend);
    }

    @GetMapping("/getAccessiblePostIds/{userInfoId}")
    public List<Integer> getAccessiblePostIds(@PathVariable int userInfoId ){
        return profileService.getAccessiblePostIds(userInfoId);
    }
}
