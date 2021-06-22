package services.profileservices.service.implementation;

import org.springframework.stereotype.Service;
import services.profileservices.client.AuthClient;
import services.profileservices.dto.ProfileDTO;
import services.profileservices.model.Profile;
import services.profileservices.model.ProfileCategory;
import services.profileservices.repository.ProfileRepository;
import services.profileservices.service.IProfileService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService implements IProfileService {

    private final ProfileRepository profileRepository;
    private final AuthClient authClient;

    public ProfileService(ProfileRepository profileRepository, AuthClient authClient){this.profileRepository = profileRepository;
        this.authClient = authClient;
    }

    @Override
    public Boolean createProfile(int userInfoId) {
        Profile profile = new Profile();
        profile.setUserInfoId(userInfoId);
        profile.setIsPrivate(false);
        profile.setCanBeMessaged(true);
        profile.setCanBeTagged(true);
        profile.setPostIds(new ArrayList<>());
        profile.setStoryIds(new ArrayList<>());
        profile.setFollowing(new ArrayList<>());
        profile.setFollowers(new ArrayList<>());
        profile.setFriends(new ArrayList<>());
        profile.setBlocked(new ArrayList<>());
        profile.setMuted(new ArrayList<>());
        Profile savedProfile = profileRepository.save(profile);
        if(savedProfile != null){
            return true;
        }else return false;
    }

    @Override
    public Boolean addPost(int postId, int userInfoId) {
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        List<Integer> ids = profile.getPostIds();
        int old_ids_len = ids.size();
        ids.add(postId);
        profile.setPostIds(ids);
        int new_ids_len = profileRepository.save(profile).getPostIds().size();
        if(new_ids_len > old_ids_len)
            return true;
        else return false;
    }

    @Override
    public Profile getByUserInfoId(int userInfoId) {
        return profileRepository.findOneByUserInfoId(userInfoId);
    }

    @Override
    public boolean checkFollowing(int loggedIn, int current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        Profile currentProfile = profileRepository.findOneByUserInfoId(current);

        for(Profile profile : loggedInProfile.getFollowing())
        {
            if(profile.getId() == currentProfile.getId())
                return true;
        }
        return false;
    }

    @Override
    public ProfileDTO getProfile(int userInfoId) {
        ProfileDTO profileDTO = authClient.getUserInfo(userInfoId);
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        profileDTO.setBiography(profile.getBiography());
        profileDTO.setWebsite(profile.getWebsite());
        profileDTO.setCanBeMessaged(profile.getCanBeMessaged());
        profileDTO.setCanBeTagged(profile.getCanBeTagged());
        profileDTO.setIsPrivate(profile.getIsPrivate());
        return profileDTO;
    }

    @Override
    public Boolean editProfile(ProfileDTO profileDTO) {
        if(!profileDTO.getUsernameChanged()) {
            authClient.edit(profileDTO);
            Profile profile = profileRepository.findOneByUserInfoId(profileDTO.getId());
            profile.setBiography(profileDTO.getBiography());
            profile.setCanBeMessaged(profileDTO.getCanBeMessaged());
            profile.setCanBeTagged(profileDTO.getCanBeTagged());
            profile.setIsPrivate(profileDTO.getIsPrivate());
            profile.setWebsite(profileDTO.getWebsite());
            profileRepository.save(profile);
            return true;
        }
        else {
            if(authClient.checkUsername(profileDTO.getUsername())) {
                authClient.edit(profileDTO);
                Profile profile = profileRepository.findOneByUserInfoId(profileDTO.getId());
                profile.setBiography(profileDTO.getBiography());
                profile.setCanBeMessaged(profileDTO.getCanBeMessaged());
                profile.setCanBeTagged(profileDTO.getCanBeTagged());
                profile.setIsPrivate(profileDTO.getIsPrivate());
                profile.setWebsite(profileDTO.getWebsite());
                profileRepository.save(profile);
                return true;
            }
            else{
                return false;
            }
        }
    }

    @Override
    public Profile editCategory(int profileId, ProfileCategory profileCategory) {
        Profile profile = profileRepository.findOneById(profileId);
        profile.setCategory(profileCategory);
        return profileRepository.save(profile);
    }


}
