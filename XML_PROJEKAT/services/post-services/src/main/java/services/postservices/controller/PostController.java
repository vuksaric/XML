package services.postservices.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public Integer createPost(@RequestParam("file") MultipartFile multipartFile, @RequestParam("location") String location, @RequestParam("caption") String caption, @RequestParam("userInfoId") String userInfoId) throws Exception{
        return postService.save(multipartFile, location, caption, userInfoId);
    }

    @PostMapping("/getPosts")
    public List<PostResponse> getPostsByPostIds(@RequestBody ProfilePostRequest profilePostRequest){
        return postService.getPostsByPostIds(profilePostRequest);
    }
}
