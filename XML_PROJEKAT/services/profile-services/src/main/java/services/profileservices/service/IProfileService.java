package services.profileservices.service;

import services.profileservices.dto.ProfileDTO;
import services.profileservices.dto.ProfileSettingsDTO;
import services.profileservices.dto.ViewProfileDTO;
import services.profileservices.model.Profile;
import services.profileservices.model.ProfileCategory;
import services.profileservices.model.VerificationRequest;

import java.util.List;

public interface IProfileService {
    Boolean createProfile(int userInfoId);
    Boolean addPost(int postId, int userInfoId);
    Boolean addStory(int storyId, int userInfoId);
    Profile getByUserInfoId(int userInfoId);
    boolean checkFollowing(int loggedIn, int current);
    boolean checkBlocked(int loggedIn, int current);
    void blockProfile(int loggedIn, int current);
    boolean checkMuted(int loggedIn, int current);
    void muteProfile(int loggedIn, int current);
    void unmuteProfile(int loggedIn, int current);
    ProfileDTO getProfile(int userInfoId);
    ViewProfileDTO getProfileByUsername(String username);
    Boolean editProfile(ProfileDTO profileDTO);
    void followProfile(int loggedIn, int current);
    void unfollowProfile(int loggedIn, int current);
    void acceptFollowRequest(int to, int from);
    void denyFollowRequest(int to, int from);
    Profile editCategory(int profileId, ProfileCategory profileCategory);
    List<String> getProfilesForTagging(int userInfoId);
    List<String> getPublicProfiles();
    List<Integer> findByUsername(List<String> usernames);
    List<String> getCloseFriends(int userInfoId);
    List<String> getProfilesForCloseFriends(int userInfoId);
    void addCloseFriend(int loggedIn, String closeFriend);
    void removeCloseFriend(int loggedIn, String closeFriend);
    List<String> getFollowingProfiles(int userInfoId);
    List<ProfileSettingsDTO> getProfilesForSettings(int userInfoId);
    void mutePost(int loggedIn, String current);
    void unmutePost(int loggedIn, String current);
    void muteStory(int loggedIn, String current);
    void unmuteStory(int loggedIn, String current);
    void muteComment(int loggedIn, String current);
    void unmuteComment(int loggedIn, String current);
    void muteMessage(int loggedIn, String current);
    void unmuteMessage(int loggedIn, String current);
    List<Integer> getAccessiblePostIds(int userInfoId);
}
