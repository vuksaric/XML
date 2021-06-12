package services.profileservices.service;

import services.profileservices.model.Profile;

public interface IProfileService {
    Boolean createProfile(int userInfoId);
    Boolean addPost(int postId, int userInfoId);
}
