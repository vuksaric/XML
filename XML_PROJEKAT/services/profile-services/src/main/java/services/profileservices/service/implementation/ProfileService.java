package services.profileservices.service.implementation;

import org.springframework.stereotype.Service;
import services.profileservices.client.AuthClient;
import services.profileservices.dto.ProfileDTO;
import services.profileservices.client.NotificationClient;
import services.profileservices.model.Profile;
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
        if (!profileDTO.getUsernameChanged()) {
            authClient.edit(profileDTO);
            Profile profile = profileRepository.findOneByUserInfoId(profileDTO.getId());
            profile.setBiography(profileDTO.getBiography());
            profile.setCanBeMessaged(profileDTO.getCanBeMessaged());
            profile.setCanBeTagged(profileDTO.getCanBeTagged());
            profile.setIsPrivate(profileDTO.getIsPrivate());
            profile.setWebsite(profileDTO.getWebsite());
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
        notificationClient.delete(to,from);
    }

    @Override
    public List<Integer> getPostIdsFeed(int userInfoId) {
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        List<Integer> result = new ArrayList<>();
        for(Integer id : profile.getFollowing())
        {
            result.addAll(profileRepository.findOneByUserInfoId(id).getPostIds());
        }
        return result;
    }


}
