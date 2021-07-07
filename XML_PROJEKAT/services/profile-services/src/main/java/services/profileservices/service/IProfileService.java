package services.profileservices.service;

import services.profileservices.dto.FavouriteRequest;
import services.profileservices.dto.FavouriteResponse;
import services.profileservices.dto.ProfileDTO;
import services.profileservices.dto.ViewProfileDTO;
import services.profileservices.model.FavouriteCollection;
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
    List<Integer> getPostIdsFeed(int userInfoId);
    List<String> getTaggedUsernames(List<Integer> taggedIds);
    List<String> getCollections(int profileId);
    boolean checkFavourite(int userInfoId, int postId);
    void addFavourite(FavouriteRequest request);
    FavouriteResponse getFavourites(int userInfoId);
    boolean checkCloseFriends(int loggedIn, int current);
    void shutDownProfile(String username);
    void removePost(int postId, String username);

}
