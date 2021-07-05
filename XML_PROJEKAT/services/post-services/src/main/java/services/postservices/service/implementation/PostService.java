package services.postservices.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import services.postservices.client.PictureVideoClient;
import services.postservices.client.ProfileClient;
import services.postservices.dto.CommentRequest;
import services.postservices.dto.ImageDTO;
import services.postservices.dto.PostResponse;
import services.postservices.dto.ProfilePostRequest;
import services.postservices.model.Comment;
import services.postservices.model.Post;
import services.postservices.model.PostInfo;
import services.postservices.repository.PostRepository;
import services.postservices.service.IPostService;

import java.io.File;
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

    @Autowired
    public PostService(PostRepository postRepository, PictureVideoClient pictureVideoClient, ProfileClient profileClient){this.postRepository = postRepository;
        this.pictureVideoClient = pictureVideoClient;
        this.profileClient = profileClient;
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
                String src = pictureVideoClient.getLocationById(idPicture);
                postResponse.getContentSrcs().add(src);
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
    public void report(int userId, int postId) {
        Post post = postRepository.getById(postId);
        post.getReportIds().add(userId);
        postRepository.save(post);
    }

    @Override
    public PostResponse addComment(CommentRequest commentRequest) {
        Post post = postRepository.getById(commentRequest.getPostId());
        post.getComments().add(new Comment(commentRequest.getUsername(),commentRequest.getContent()));
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
                        String src = pictureVideoClient.getLocationById(idPicture);
                        postResponse.getContentSrcs().add(src);
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
                        String src = pictureVideoClient.getLocationById(idPicture);
                        postResponse.getContentSrcs().add(src);
                    }
                    result.add(postResponse);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<PostResponse> getForFeed(List<Integer> postIds) {
        List<PostResponse> result = new ArrayList<>();
        for(Integer id : postIds)
        {
            Post post = postRepository.findOneById(id);
            PostResponse postResponse = new PostResponse(post);
            for(Integer idPicture : post.getPostInfo().getPictureIds())
            {
                String src = pictureVideoClient.getLocationById(idPicture);
                postResponse.getContentSrcs().add(src);
            }
            result.add(postResponse);
        }

        Collections.sort(result, Collections.reverseOrder());
        return result;
    }
}
