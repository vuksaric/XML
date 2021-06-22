package services.profileservices.service;

import services.profileservices.dto.ProfileDTO;
import services.profileservices.model.Profile;
import services.profileservices.model.VerificationRequest;

import java.util.List;

public interface IProfileService {
    Boolean createProfile(int userInfoId);
    Boolean addPost(int postId, int userInfoId);
    Profile getByUserInfoId(int userInfoId);
    boolean checkFollowing(int loggedIn, int current);
    ProfileDTO getProfile(int userInfoId);
    Boolean editProfile(ProfileDTO profileDTO);
    void followProfile(int loggedIn, int current);
    void unfollowProfile(int loggedIn, int current);
    void acceptFollowRequest(int to, int from);
    void denyFollowRequest(int to, int from);

}
