package services.postservices.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import services.postservices.dto.*;
import services.postservices.model.Comment;
import services.postservices.model.Post;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    int save(MultipartFile[] multipartFile, String location, String caption, String userInfoId,List<String> tags) throws IOException;
    List<PostResponse> getPostsByPostIds(ProfilePostRequest profilePostRequest);
    List<Post> getALlPublic();
    boolean isItLiked(int userId, int postId);
    boolean isItDisliked(int userId, int postId);
    boolean isItReported(int userId, int postId);
    void like(int userId, int postId);
    void dislike(int userId, int postId);
    void report(int userId, int postId,String username);
    PostResponse addComment(CommentRequest commentRequest);
    List<PostResponse> getLikedByProfile(int userId);
    List<PostResponse> getDislikedByProfile(int userId);
    List<PostResponse> getForFeed(List<FeedPostRequest> requests);
    List<PostResponse> getTagsPost(String username);
    List<String> getLocations(int userInfoId);
    List<PostResponse> getPostsByLocation(int userInfoId,String location);
    void removePost(int id, String username);
    int newPostCommercial(MultipartFile[] multipartFile, String caption, List<String> tags, String userInfoId) throws IOException;
    List<PostResponse> getPostCommercials(List<CampaignRequest> requests);
}
