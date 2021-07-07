package services.postservices.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.postservices.dto.CommentRequest;
import services.postservices.dto.PostResponse;
import services.postservices.dto.ProfilePostRequest;
import services.postservices.model.Post;
import services.postservices.service.IPostService;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final IPostService postService;
    public PostController(IPostService postService){this.postService = postService;}

    @PostMapping("/create")
    public Integer createPost(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("location") String location, @RequestParam("caption") String caption,
                              @RequestParam("userInfoId") String userInfoId, @RequestParam("tags") List<String> tags) throws Exception{
        return postService.save(multipartFile, location, caption, userInfoId,  tags);
    }

    @PostMapping("/getPosts")
    public List<PostResponse> getPostsByPostIds(@RequestBody ProfilePostRequest profilePostRequest){
        return postService.getPostsByPostIds(profilePostRequest);
    }

    @GetMapping("/getAllPublic")
    public List<Post> getAllPublic() {
        return postService.getALlPublic();
    }

    @PutMapping("/isItLiked/{userId}/{postId}")
    public boolean isItLiked(@PathVariable int userId, @PathVariable int postId){
        return postService.isItLiked(userId,postId);
    }

    @PutMapping("/isItDisliked/{userId}/{postId}")
    public boolean isItDisliked(@PathVariable int userId, @PathVariable int postId){
        return postService.isItDisliked(userId,postId);
    }

    @PutMapping("/isItReported/{userId}/{postId}")
    public boolean isItReported(@PathVariable int userId, @PathVariable int postId){
        return postService.isItReported(userId,postId);
    }

    @PutMapping("/like/{userId}/{postId}")
    public void like(@PathVariable int userId, @PathVariable int postId){
         postService.like(userId,postId);
    }

    @PutMapping("/dislike/{userId}/{postId}")
    public void dislike(@PathVariable int userId, @PathVariable int postId){
        postService.dislike(userId,postId);
    }

    @PutMapping("/report/{userId}/{postId}/{username}")
    public void report(@PathVariable int userId, @PathVariable int postId, @PathVariable String username){
        postService.report(userId,postId,username);
    }

    @PutMapping("/addComment")
    public PostResponse addComment(@RequestBody CommentRequest commentRequest){
        return postService.addComment(commentRequest);
    }

    @GetMapping("/likedByProfile/{userId}")
    public List<PostResponse> likedByProfile(@PathVariable int userId){
        return postService.getLikedByProfile(userId);
    }

    @GetMapping("/dislikedByProfile/{userId}")
    public List<PostResponse> dislikedByProfile(@PathVariable int userId){
        return postService.getDislikedByProfile(userId);
    }

    @PostMapping("/getForFeed")
    public List<PostResponse> getForFeed(@RequestBody ProfilePostRequest profilePostRequest){
        return postService.getForFeed(profilePostRequest.getPostIds());
    }

    @PutMapping("/remove/{id}/{username}")
    public void remove(@PathVariable int id, @PathVariable String username){
         postService.removePost(id,username);
    }

}
