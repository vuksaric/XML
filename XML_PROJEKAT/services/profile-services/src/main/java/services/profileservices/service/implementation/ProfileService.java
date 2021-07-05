package services.profileservices.service.implementation;

import org.springframework.stereotype.Service;
import services.profileservices.client.AuthClient;
import services.profileservices.dto.ProfileDTO;
import services.profileservices.client.NotificationClient;
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
    private final NotificationClient notificationClient;

    public ProfileService(ProfileRepository profileRepository, AuthClient authClient, NotificationClient notificationClient)
    {
        this.profileRepository = profileRepository;
        this.authClient = authClient;
        this.notificationClient = notificationClient;

    }

    @Override
    public Boolean createProfile(int userInfoId) {
        Profile profile = new Profile();
        profile.setUserInfoId(userInfoId);
        profile.setIsPrivate(false);
        profile.setCanBeMessaged(true);
        profile.setCanBeTagged(true);
        profile.setCanBeMessagedPrivate(true);
        profile.setNotifyProfileActivity(true);
        profile.setNotifyComment(true);
        profile.setNotifyPost(true);
        profile.setNotifyStory(true);
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

        for(Integer profile : loggedInProfile.getFollowing())
        {
            if(profile == currentProfile.getId())
                return true;
        }
        return false;
    }

    @Override
    public boolean checkBlocked(int loggedIn, int current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        Profile currentProfile = profileRepository.findOneByUserInfoId(current);

        for(Integer profile : loggedInProfile.getBlocked())
        {
            if(profile == currentProfile.getId())
                return true;
        }
        return false;
    }

    @Override
    public void blockProfile(int loggedIn, int current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        Profile currentProfile = profileRepository.findOneByUserInfoId(current);

        loggedInProfile.getBlocked().add(currentProfile.getId());
        unfollowProfile(loggedIn, current);
        profileRepository.save(loggedInProfile);
    }

    @Override
    public boolean checkMuted(int loggedIn, int current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        Profile currentProfile = profileRepository.findOneByUserInfoId(current);

        for(Integer profile : loggedInProfile.getMuted())
        {
            if(profile == currentProfile.getId())
                return true;
        }
        return false;
    }

    @Override
    public void muteProfile(int loggedIn, int current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        Profile currentProfile = profileRepository.findOneByUserInfoId(current);

        loggedInProfile.getMuted().add(currentProfile.getId());
        profileRepository.save(loggedInProfile);
    }

    @Override
    public void unmuteProfile(int loggedIn, int current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        Profile currentProfile = profileRepository.findOneByUserInfoId(current);

        for(int i= 0; i < loggedInProfile.getMuted().size(); i++)
        {
            if(loggedInProfile.getMuted().get(i) == currentProfile.getId())
            {
                loggedInProfile.getMuted().remove(i);
                break;
            }
        }

        profileRepository.save(loggedInProfile);
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
        profileDTO.setCanBeMessagedPrivate(profile.getCanBeMessagedPrivate());
        profileDTO.setNotifyProfileActivity(profile.getNotifyProfileActivity());
        profileDTO.setNotifyComment(profile.getNotifyComment());
        profileDTO.setNotifyPost(profile.getNotifyPost());
        profileDTO.setNotifyStory(profile.getNotifyStory());
        return profileDTO;
    }

    @Override
    public Boolean editProfile(ProfileDTO profileDTO) {
        if (!profileDTO.getUsernameChanged()) {
            authClient.edit(profileDTO);
            Profile profile = profileRepository.findOneByUserInfoId(profileDTO.getId());
            profile.setBiography(profileDTO.getBiography());
            profile.setCanBeMessaged(profileDTO.getCanBeMessaged());
            profile.setCanBeTagged(profileDTO.getCanBeTagged());
            profile.setIsPrivate(profileDTO.getIsPrivate());
            profile.setWebsite(profileDTO.getWebsite());
            profile.setCanBeMessagedPrivate(profileDTO.getCanBeMessagedPrivate());
            profile.setNotifyProfileActivity(profileDTO.getNotifyProfileActivity());
            profile.setNotifyComment(profileDTO.getNotifyComment());
            profile.setNotifyPost(profileDTO.getNotifyPost());
            profile.setNotifyStory(profileDTO.getNotifyStory());

            profileRepository.save(profile);
            return true;
        } else {
            if (authClient.checkUsername(profileDTO.getUsername())) {
                authClient.edit(profileDTO);
                Profile profile = profileRepository.findOneByUserInfoId(profileDTO.getId());
                profile.setBiography(profileDTO.getBiography());
                profile.setCanBeMessaged(profileDTO.getCanBeMessaged());
                profile.setCanBeTagged(profileDTO.getCanBeTagged());
                profile.setIsPrivate(profileDTO.getIsPrivate());
                profile.setWebsite(profileDTO.getWebsite());
                profile.setCanBeMessagedPrivate(profileDTO.getCanBeMessagedPrivate());
                profile.setNotifyProfileActivity(profileDTO.getNotifyProfileActivity());
                profile.setNotifyComment(profileDTO.getNotifyComment());
                profile.setNotifyPost(profileDTO.getNotifyPost());
                profile.setNotifyStory(profileDTO.getNotifyStory());
                profileRepository.save(profile);
                return true;
            } else {
                return false;
            }
        }
    }
    public void followProfile(int loggedIn, int current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        Profile currentProfile = profileRepository.findOneByUserInfoId(current);

        loggedInProfile.getFollowing().add(currentProfile.getId());
        currentProfile.getFollowers().add(loggedInProfile.getId());
        profileRepository.save(loggedInProfile);
        profileRepository.save(currentProfile);

    }

    @Override
    public void unfollowProfile(int loggedIn, int current) {

        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        Profile currentProfile = profileRepository.findOneByUserInfoId(current);

        for(int i= 0; i < loggedInProfile.getFollowing().size(); i++)
        {
            if(loggedInProfile.getFollowing().get(i) == currentProfile.getId())
            {
                loggedInProfile.getFollowing().remove(i);
                break;
            }
        }
        for(int i= 0; i < currentProfile.getFollowers().size(); i++)
        {
            if(currentProfile.getFollowers().get(i) == loggedInProfile.getId())
            {
                currentProfile.getFollowers().remove(i);
                break;
            }
        }
        if(loggedInProfile.getIsPrivate() && currentProfile.getIsPrivate())
        {
            for(int i= 0; i < currentProfile.getFollowing().size(); i++)
            {
                if(currentProfile.getFollowing().get(i) == loggedInProfile.getId())
                {
                    currentProfile.getFollowing().remove(i);
                    break;
                }
            }
            for(int i= 0; i < loggedInProfile.getFollowers().size(); i++)
            {
                if(loggedInProfile.getFollowers().get(i) == currentProfile.getId())
                {
                    loggedInProfile.getFollowers().remove(i);
                    break;
                }
            }
        }
        profileRepository.save(loggedInProfile);
        profileRepository.save(currentProfile);

    }

    @Override
    public void acceptFollowRequest(int to, int from) {
        Profile toProfile = profileRepository.findOneByUserInfoId(to);
        Profile fromProfile = profileRepository.findOneByUserInfoId(from);

        fromProfile.getFollowing().add(toProfile.getId());
        toProfile.getFollowers().add(fromProfile.getId());
        fromProfile.getFollowers().add(toProfile.getId());
        toProfile.getFollowing().add(fromProfile.getId());
        profileRepository.save(toProfile);
        profileRepository.save(fromProfile);
        notificationClient.delete(to,from);

    }

    @Override
    public void denyFollowRequest(int to, int from) {
        notificationClient.delete(to, from);
    }

    @Override
    public Profile editCategory(int profileId, ProfileCategory profileCategory) {
        Profile profile = profileRepository.findOneById(profileId);
        profile.setCategory(profileCategory);
        return profileRepository.save(profile);
    }


}
