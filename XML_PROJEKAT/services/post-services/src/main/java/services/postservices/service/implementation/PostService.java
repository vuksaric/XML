package services.postservices.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import services.postservices.client.NotificationClient;
import services.postservices.client.PictureVideoClient;
import services.postservices.client.ProfileClient;
import services.postservices.dto.*;
import services.postservices.model.Comment;
import services.postservices.model.Post;
import services.postservices.model.PostInfo;
import services.postservices.repository.PostRepository;
import services.postservices.service.IPostService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private final PictureVideoClient pictureVideoClient;
    private final ProfileClient profileClient;
    private final NotificationClient notificationClient;



    @Autowired
    public PostService(PostRepository postRepository, PictureVideoClient pictureVideoClient, ProfileClient profileClient, NotificationClient notificationClient){
        this.postRepository = postRepository;
        this.pictureVideoClient = pictureVideoClient;
        this.profileClient = profileClient;
        this.notificationClient = notificationClient;
    }

    @Override
    public int save(MultipartFile[] multipartFile, String location, String caption, String userInfoId, List<String> tags) throws IOException {
        Post p = new Post();
        List<Integer> ids = new ArrayList<>();

        for(MultipartFile file : multipartFile){
            int contentId;
            if(file.getContentType().contains("image"))
                contentId = pictureVideoClient.uploadImage(new ImageDTO(file.getOriginalFilename(),file.getBytes(), true));
            else
                contentId = pictureVideoClient.uploadImage(new ImageDTO(file.getOriginalFilename(),file.getBytes(), false));
            ids.add(contentId);
        }

        List<Integer> taggedIds = profileClient.findByUsername(tags);

        PostInfo postInfo = new PostInfo();
        postInfo.setPictureIds(ids);
        postInfo.setCaption(caption);
        postInfo.setLocation(location);
        postInfo.setDate(LocalDate.now());
        postInfo.setTaggedIds(taggedIds);
        p.setPostInfo(postInfo);
        Post new_post = postRepository.save(p);
        int userId = Integer.parseInt(userInfoId);
        profileClient.addPost(new_post.getId(), userId);
        return new_post.getId();
    }

    @Override
    public List<PostResponse> getPostsByPostIds(ProfilePostRequest profilePostRequest) {
        List<PostResponse> postList = new ArrayList<>();
        for(int id : profilePostRequest.getPostIds()){
            Post post = postRepository.findOneById(id);
            PostResponse postResponse = new  PostResponse(post);
            for(Integer idPicture : post.getPostInfo().getPictureIds())
            {
                boolean image = pictureVideoClient.getImageById(idPicture);
                String src = pictureVideoClient.getLocationById(idPicture);
                PictureDTO pictureDTO = new PictureDTO(src,image);
                postResponse.getContent().add(pictureDTO);
                List<String> taggedUsernames = profileClient.getTaggedUsernames(post.getPostInfo().getTaggedIds());
                postResponse.setTagged(taggedUsernames);
            }
            postList.add(postResponse);
        }
        return postList;
    }

    @Override
    public List<Post> getALlPublic() {
        //ovde dodati komunikaciju sa profile mikroservisom, samo public mozemo da prikazujemo
        return null;
    }
    public boolean isItLiked(int userId, int postId) {
        Post post = postRepository.getById(postId);
        for(Integer id : post.getLikeIds())
        {
            if(id == userId)
                return true;
        }
        return false;
    }

    @Override
    public boolean isItDisliked(int userId, int postId) {
        Post post = postRepository.getById(postId);
        for(Integer id : post.getDislikeIds())
        {
            if(id == userId)
                return true;
        }
        return false;
    }

    @Override
    public boolean isItReported(int userId, int postId) {
        Post post = postRepository.getById(postId);
        for(Integer id : post.getReportIds())
        {
            if(id == userId)
                return true;
        }
        return false;
    }

    @Override
    public void like(int userId, int postId) {
        Post post = postRepository.getById(postId);
        for(int i= 0; i < post.getDislikeIds().size(); i++)
        {
            if(post.getDislikeIds().get(i) == userId)
                post.getDislikeIds().remove(i);
        }
        post.getLikeIds().add(userId);
        postRepository.save(post);
    }

    @Override
    public void dislike(int userId, int postId) {
        Post post = postRepository.getById(postId);
        for(int i= 0; i < post.getLikeIds().size(); i++)
        {
            if(post.getLikeIds().get(i) == userId)
                post.getLikeIds().remove(i);
        }
        post.getDislikeIds().add(userId);
        postRepository.save(post);
    }

    @Override
    public void report(int userId, int postId,String username) {
        Post post = postRepository.getById(postId);
        post.getReportIds().add(userId);
        postRepository.save(post);
        notificationClient.save(postId,username);
    }

    @Override
    public PostResponse addComment(CommentRequest commentRequest) {
        Post post = postRepository.getById(commentRequest.getPostId());
        List<Integer> taggedIds = profileClient.findByUsername(commentRequest.getTaggedUsernames());
        post.getComments().add(new Comment(commentRequest.getUsername(),commentRequest.getContent(), taggedIds));
        return new PostResponse(postRepository.save(post));
    }

    @Override
    public List<PostResponse> getLikedByProfile(int userId) {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> result = new ArrayList<>();
        for(Post post : posts)
        {
            for(Integer id : post.getLikeIds())
            {
                if(id == userId)
                {
                    PostResponse postResponse = new PostResponse(post);
                    for(Integer idPicture : post.getPostInfo().getPictureIds())
                    {
                        boolean image = pictureVideoClient.getImageById(idPicture);
                        String src = pictureVideoClient.getLocationById(idPicture);
                        PictureDTO pictureDTO = new PictureDTO(src,image);
                        postResponse.getContent().add(pictureDTO);
                        List<String> taggedUsernames = profileClient.getTaggedUsernames(post.getPostInfo().getTaggedIds());
                        postResponse.setTagged(taggedUsernames);
                    }
                    result.add(postResponse);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<PostResponse> getDislikedByProfile(int userId) {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> result = new ArrayList<>();
        for(Post post : posts)
        {
            for(Integer id : post.getDislikeIds())
            {
                if(id == userId)
                {
                    PostResponse postResponse = new PostResponse(post);
                    for(Integer idPicture : post.getPostInfo().getPictureIds())
                    {
                        boolean image = pictureVideoClient.getImageById(idPicture);
                        String src = pictureVideoClient.getLocationById(idPicture);
                        PictureDTO pictureDTO = new PictureDTO(src,image);
                        postResponse.getContent().add(pictureDTO);
                        List<String> taggedUsernames = profileClient.getTaggedUsernames(post.getPostInfo().getTaggedIds());
                        postResponse.setTagged(taggedUsernames);
                    }
                    result.add(postResponse);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<PostResponse> getForFeed(List<FeedPostRequest> requests) {
        List<PostResponse> result = new ArrayList<>();
        for(FeedPostRequest request : requests)
        {
            Post post = postRepository.findOneById(request.getPostId());
            PostResponse postResponse = new PostResponse(post);
            postResponse.setUsername(request.getUsername());
            for(Integer idPicture : post.getPostInfo().getPictureIds())
            {
                boolean image = pictureVideoClient.getImageById(idPicture);
                String src = pictureVideoClient.getLocationById(idPicture);
                PictureDTO pictureDTO = new PictureDTO(src,image);
                postResponse.getContent().add(pictureDTO);
            }
            List<String> taggedUsernames = profileClient.getTaggedUsernames(post.getPostInfo().getTaggedIds());
            postResponse.setTagged(taggedUsernames);
            result.add(postResponse);
        }

        Collections.sort(result, Collections.reverseOrder());
        return result;
    }

    public List<PostResponse> getTagsPost(String username) {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> result = new ArrayList<>();

        List<String> usernames = new ArrayList<>();
        usernames.add(username);
        List<Integer> ids = profileClient.findByUsername(usernames);

        for(Post post : posts){
            if(post.getPostInfo().getTaggedIds().contains(ids.get(0))) {
                PostResponse postResponse = new PostResponse(post);
                for (Integer idPicture : post.getPostInfo().getPictureIds()) {
                    boolean image = pictureVideoClient.getImageById(idPicture);
                    String src = pictureVideoClient.getLocationById(idPicture);
                    PictureDTO pictureDTO = new PictureDTO(src,image);
                    postResponse.getContent().add(pictureDTO);
                }
                result.add(postResponse);
            }
            if(getTagsComment(post, ids.get(0))){
                PostResponse postResponse = new PostResponse(post);
                for (Integer idPicture : post.getPostInfo().getPictureIds()) {
                    boolean image = pictureVideoClient.getImageById(idPicture);
                    String src = pictureVideoClient.getLocationById(idPicture);
                    PictureDTO pictureDTO = new PictureDTO(src,image);
                    postResponse.getContent().add(pictureDTO);
                }
                if(!result.contains(postResponse))
                    result.add(postResponse);
            }

        }
        return result;
    }

    @Override
    public List<String> getLocations(int userInfoId) {
        List<String> locations = new ArrayList<>();
        List<Integer> postIds = profileClient.getAccessiblePostIds(userInfoId);
        for(Integer id : postIds){
            if(!locations.contains(postRepository.findOneById(id).getPostInfo().getLocation()))
                locations.add(postRepository.findOneById(id).getPostInfo().getLocation());
        }
        return locations;
    }

    @Override
    public List<PostResponse> getPostsByLocation(int userInfoId,String location) {
        List<PostResponse> result = new ArrayList<>();
        List<Integer> postIds = profileClient.getAccessiblePostIds(userInfoId);
        for(Integer id : postIds){
            Post post = postRepository.findOneById(id);
            if(post.getPostInfo().getLocation().equals(location)){
                PostResponse postResponse = new PostResponse(post);
                for (Integer idPicture : post.getPostInfo().getPictureIds()) {
                    boolean image = pictureVideoClient.getImageById(idPicture);
                    String src = pictureVideoClient.getLocationById(idPicture);
                    PictureDTO pictureDTO = new PictureDTO(src,image);
                    postResponse.getContent().add(pictureDTO);
                }
                result.add(postResponse);
            }
        }
        return result;
    }

    private boolean getTagsComment(Post post, int id) {
        for (Comment comment : post.getComments()) {
            if (comment.getTaggedIds().contains(id))
                return true;
        }
        return false;
    }

    @Override
    public void removePost(int id, String username) {
        Post post = postRepository.getById(id);
        profileClient.removePost(id,username);
        postRepository.delete(post);
    }
}
