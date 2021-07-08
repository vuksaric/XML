package services.profileservices.service.implementation;

import org.springframework.stereotype.Service;
import services.profileservices.client.AuthClient;
import services.profileservices.dto.*;
import services.profileservices.client.NotificationClient;
import services.profileservices.model.FavouriteCollection;
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
        profile.setNotifyProfileActivity(true);
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
    public Boolean addStory(int storyId, int userInfoId) {
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        List<Integer> ids = profile.getStoryIds();
        int old_ids_len = ids.size();
        ids.add(storyId);
        profile.setStoryIds(ids);
        int new_ids_len = profileRepository.save(profile).getStoryIds().size();
        if(new_ids_len > old_ids_len)
            return true;
        else
            return false;
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
        profileDTO.setNotifyProfileActivity(profile.getNotifyProfileActivity());
        return profileDTO;
    }

    @Override
    public ViewProfileDTO getProfileByUsername(String username) {
        ViewProfileDTO profileDTO = authClient.getUserInfoByUsername(username);
        Profile profile = profileRepository.findOneByUserInfoId(profileDTO.getId());
        if(profile.isShutDown())
        {
            return null;
        }
        profileDTO.setBiography(profile.getBiography());
        profileDTO.setWebsite(profile.getWebsite());
        profileDTO.setIsPrivate(profile.getIsPrivate());
        profileDTO.setPostIds(profile.getPostIds());
        profileDTO.setStoryIds(profile.getStoryIds());
        profileDTO.setFollowers(profile.getFollowers());
        profileDTO.setFollowing(profile.getFollowing());
        profileDTO.setBlocked(profile.getBlocked());
        profileDTO.setFriends(profile.getFriends());
        profileDTO.setMuted(profile.getMuted());
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
            profile.setNotifyProfileActivity(profileDTO.getNotifyProfileActivity());
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
                profile.setNotifyProfileActivity(profileDTO.getNotifyProfileActivity());
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

    @Override
    public List<String> getProfilesForTagging(int userInfoId) {
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        List<String> result = new ArrayList<>();

        for(Integer id : profile.getFollowing()){
            if(profileRepository.findOneById(id).getCanBeTagged())
                result.add(authClient.getUsername(id));
        }

        for(Profile p : profileRepository.findAllPublicAndCanBeTagged()){
            String username = authClient.getUsername(p.getUserInfoId());
            if(!result.contains(username))
                result.add(username);
        }

        return result;
    }

    @Override
    public List<String> getPublicProfiles() {
        List<String> result = new ArrayList<>();
        for(Profile p : profileRepository.findAllPublic()){
            result.add(authClient.getUsername(p.getUserInfoId()));
        }
        return result;
    }

    @Override
    public List<Integer> findByUsername(List<String> usernames) {
        List<Integer> result = new ArrayList<>();
        for(Integer i : authClient.getUserInfoIds(usernames)){
            result.add(profileRepository.findOneByUserInfoId(i).getId());
        }
        return result;
    }

    @Override
    public List<FeedPostRequest> getPostIdsFeed(int userInfoId) {
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        List<FeedPostRequest> result = new ArrayList<>();
        for(Integer id : profile.getFollowing()) {
            Profile following = profileRepository.findOneById(id);
            String username = authClient.getUsername(following.getUserInfoId());
            for(Integer postId : profileRepository.findOneByUserInfoId(id).getPostIds())
            {
                FeedPostRequest request = new FeedPostRequest(postId,username);
                result.add(request);
            }

        }
        return result;
    }
    public List<String> getCloseFriends(int userInfoId) {
        List<String> result = new ArrayList<>();
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        for(Integer i : profile.getFriends()){
            int id = profileRepository.findOneById(i).getUserInfoId();
            result.add(authClient.getUsername(id));

        }
        return result;
    }

    @Override
    public List<String> getTaggedUsernames(List<Integer> taggedIds) {
            List<String> result = new ArrayList<>();
            for (Integer id : taggedIds) {
                Profile profile = profileRepository.findOneById(id);
                String username = authClient.getUsername(id);
                result.add(username);
            }
            return result;
    }
    public List<String> getProfilesForCloseFriends(int userInfoId) {
        List<String> result = new ArrayList<>();
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);

        for(Integer i : profile.getFollowing()){
            if(!profile.getFriends().contains(i)){
                int id = profileRepository.findOneById(i).getUserInfoId();
                result.add(authClient.getUsername(id));
            }

        }

        for(Profile p : profileRepository.findAllPublic()){
            String username = authClient.getUsername(p.getUserInfoId());
            if(!result.contains(username) && !profile.getFriends().contains(p.getId()))
                result.add(username);
        }
        return result;
    }

    @Override
    public List<String> getCollections(int profileId) {
        Profile profile = profileRepository.findOneByUserInfoId(profileId);
        List<String> result = new ArrayList<>();
        for(FavouriteCollection collection : profile.getCollections()) {
            result.add(collection.getName());
        }
        return result;
    }
    public void addCloseFriend(int loggedIn, String closeFriend) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);

        List<String> usernames =  new ArrayList<>();
        usernames.add(closeFriend);
        List<Integer> closeFriendId = authClient.getUserInfoIds(usernames);
        Profile closeFriendProfile = profileRepository.findOneByUserInfoId(closeFriendId.get(0));

        loggedInProfile.getFriends().add(closeFriendProfile.getId());
        profileRepository.save(loggedInProfile);
    }

    @Override
    public void removeCloseFriend(int loggedIn, String closeFriend) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);

        List<String> usernames =  new ArrayList<>();
        usernames.add(closeFriend);
        List<Integer> closeFriendId = authClient.getUserInfoIds(usernames);
        Profile closeFriendProfile = profileRepository.findOneByUserInfoId(closeFriendId.get(0));
        for(int i= 0; i < loggedInProfile.getFriends().size(); i++)
        {
            if(loggedInProfile.getFriends().get(i) == closeFriendProfile.getId())
            {
                loggedInProfile.getFriends().remove(i);
                break;
            }
        }

        profileRepository.save(loggedInProfile);
    }

    @Override
    public List<String> getFollowingProfiles(int userInfoId) {
        List<String> result = new ArrayList<>();
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        for(Integer i : profile.getFollowing()){
            int id = profileRepository.findOneById(i).getUserInfoId();
            result.add(authClient.getUsername(id));

        }
        return result;
    }

    @Override
    public boolean checkFavourite(int userInfoId, int postId) {
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        for(Integer id : profile.getFavouriteIds())
        {
            if(id == postId)
                return true;
        }
        return false;
    }

    @Override
    public void addFavourite(FavouriteRequest request) {
        Profile profile = profileRepository.findOneByUserInfoId(request.getProfileId());
        profile.getFavouriteIds().add(request.getPostId());
        boolean exists = false;
        if(request.isCollection())
        {
            for(FavouriteCollection collection : profile.getCollections())
            {
                if(collection.getName().equals(request.getCollectionName()))
                {
                    collection.getPostIds().add(request.getPostId());
                    exists = true;
                    break;
                }
            }
            if(!exists)
            {
                FavouriteCollection newCollection = new FavouriteCollection();
                newCollection.setName(request.getCollectionName());
                newCollection.setPostIds(new ArrayList<>());
                newCollection.getPostIds().add(request.getPostId());
                profile.getCollections().add(newCollection);
            }
        }
        profileRepository.save(profile);
    }

    @Override
    public FavouriteResponse getFavourites(int userInfoId) {
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        FavouriteResponse response = new FavouriteResponse(profile.getFavouriteIds(),profile.getCollections());
        return response;
    }

    @Override
    public boolean checkCloseFriends(int loggedIn, int current){
            Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
            for (Integer id : loggedInProfile.getFriends()) {
                if (id == current)
                    return true;
            }
            return false;
    }
    public List<ProfileSettingsDTO> getProfilesForSettings(int userInfoId) {
        List<ProfileSettingsDTO> result = new ArrayList<>();
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        for(Integer i : profile.getFollowing()){
            int id = profileRepository.findOneById(i).getUserInfoId();
            boolean checkPost = checkMutedPost(userInfoId,authClient.getUsername(id) );
            boolean checkStory= checkMutedStory(userInfoId,authClient.getUsername(id) );
            boolean checkComment = checkMutedComment(userInfoId,authClient.getUsername(id) );
            boolean checkMessage = checkMutedMessage(userInfoId,authClient.getUsername(id) );
            result.add(new ProfileSettingsDTO(authClient.getUsername(id), checkPost, checkStory, checkComment, checkMessage ));
        }
        return result;
    }

    @Override
    public void mutePost(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));

        loggedInProfile.getMutedPost().add(currentProfile.getId());
        profileRepository.save(loggedInProfile);
    }

    @Override
    public void unmutePost(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));

        for(int i= 0; i < loggedInProfile.getMutedPost().size(); i++)
        {
            if(loggedInProfile.getMutedPost().get(i) == currentProfile.getId())
            {
                loggedInProfile.getMutedPost().remove(i);
                break;
            }
        }

        profileRepository.save(loggedInProfile);
    }

    @Override
    public void muteStory(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));

        loggedInProfile.getMutedStory().add(currentProfile.getId());
        profileRepository.save(loggedInProfile);
    }

    @Override
    public void unmuteStory(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));

        for(int i= 0; i < loggedInProfile.getMutedStory().size(); i++)
        {
            if(loggedInProfile.getMutedStory().get(i) == currentProfile.getId())
            {
                loggedInProfile.getMutedStory().remove(i);
                break;
            }
        }

        profileRepository.save(loggedInProfile);
    }

    @Override
    public void muteComment(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));

        loggedInProfile.getMutedComment().add(currentProfile.getId());
        profileRepository.save(loggedInProfile);
    }

    @Override
    public void unmuteComment(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));

        for(int i= 0; i < loggedInProfile.getMutedComment().size(); i++)
        {
            if(loggedInProfile.getMutedComment().get(i) == currentProfile.getId())
            {
                loggedInProfile.getMutedComment().remove(i);
                break;
            }
        }

        profileRepository.save(loggedInProfile);
    }

    @Override
    public void muteMessage(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));

        loggedInProfile.getMutedMessage().add(currentProfile.getId());
        profileRepository.save(loggedInProfile);
    }

    @Override
    public void unmuteMessage(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));

        for(int i= 0; i < loggedInProfile.getMutedMessage().size(); i++)
        {
            if(loggedInProfile.getMutedMessage().get(i) == currentProfile.getId())
            {
                loggedInProfile.getMutedMessage().remove(i);
                break;
            }
        }

        profileRepository.save(loggedInProfile);
    }

    @Override
    public List<Integer> getAccessiblePostIds(int userInfoId) {
        List<Integer> postIds = new ArrayList<>();
         Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        List<Integer> profiles = profile.getFollowing();

        List<Profile> publicProfiles = profileRepository.findAllPublic();
        for(Profile p : publicProfiles){
            if(!profiles.contains(p.getId()))
                profiles.add(p.getId());
        }

        for(Integer i : profiles){
            postIds.addAll(profileRepository.findOneById(i).getPostIds());
        }

        return postIds;

    }

    @Override
    public List<FeedStoryRequest> getStoriesFeed(int userInfoId) {
        Profile profile = profileRepository.findOneByUserInfoId(userInfoId);
        List<FeedStoryRequest> result = new ArrayList<>();
        for(Integer id : profile.getFollowing())
        {
            Profile following = profileRepository.findOneById(id);
            ProfileDTO followingDTO = authClient.getUserInfo(following.getUserInfoId());
            FeedStoryRequest request = new FeedStoryRequest(following.getStoryIds(),followingDTO.getUsername());
            result.add(request);
        }
        return result;
    }

    private boolean checkMutedPost(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));
        for(Integer profile : loggedInProfile.getMutedPost())
        {
            if(profile == currentProfile.getId())
                return true;
        }
        return false;
    }
    private boolean checkMutedStory(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));
        for(Integer profile : loggedInProfile.getMutedStory())
        {
            if(profile == currentProfile.getId())
                return true;
        }
        return false;
    }
    private boolean checkMutedComment(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));
        for(Integer profile : loggedInProfile.getMutedComment())
        {
            if(profile == currentProfile.getId())
                return true;
        }
        return false;
    }
    private boolean checkMutedMessage(int loggedIn, String current) {
        Profile loggedInProfile = profileRepository.findOneByUserInfoId(loggedIn);
        List<String> usernames =  new ArrayList<>();
        usernames.add(current);
        List<Integer> currentId = authClient.getUserInfoIds(usernames);
        Profile currentProfile = profileRepository.findOneByUserInfoId(currentId.get(0));
        for(Integer profile : loggedInProfile.getMutedMessage())
        {
            if(profile == currentProfile.getId())
                return true;
        }
        return false;

    }

    @Override
    public void shutDownProfile(String username) {
        ViewProfileDTO profileDTO = authClient.getUserInfoByUsername(username);
        Profile profile = profileRepository.findOneByUserInfoId(profileDTO.getId());
        profile.setShutDown(true);
        profileRepository.save(profile);
    }

    @Override
    public void removePost(int postId, String username) {
        ViewProfileDTO profileDTO = authClient.getUserInfoByUsername(username);
        Profile profile = profileRepository.findOneByUserInfoId(profileDTO.getId());
        for(int i= 0; i < profile.getPostIds().size(); i++)
        {
            if(profile.getPostIds().get(i) == postId)
            {
                profile.getPostIds().remove(i);
                break;
            }
        }

        profileRepository.removeFromFavourites(postId);
        profileRepository.removeFromCollection(postId);
        profileRepository.save(profile);
    }


}
