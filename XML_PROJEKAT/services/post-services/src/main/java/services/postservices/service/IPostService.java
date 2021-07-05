package services.postservices.service;

import org.springframework.web.multipart.MultipartFile;
import services.postservices.dto.CommentRequest;
import services.postservices.dto.PostResponse;
import services.postservices.dto.ProfilePostRequest;
import services.postservices.model.Comment;
import services.postservices.model.Post;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    int save(MultipartFile multipartFile, String location, String caption, String userInfoId) throws IOException;
    List<PostResponse> getPostsByPostIds(ProfilePostRequest profilePostRequest);
    boolean isItLiked(int userId, int postId);
    boolean isItDisliked(int userId, int postId);
    boolean isItReported(int userId, int postId);
    void like(int userId, int postId);
    void dislike(int userId, int postId);
    void report(int userId, int postId);
    PostResponse addComment(CommentRequest commentRequest);
    List<PostResponse> getLikedByProfile(int userId);
    List<PostResponse> getDislikedByProfile(int userId);
    List<PostResponse> getForFeed(List<Integer> postIds);
}
