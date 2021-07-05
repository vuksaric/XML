package services.postservices.service;

import org.springframework.web.multipart.MultipartFile;
import services.postservices.dto.PostResponse;
import services.postservices.dto.ProfilePostRequest;
import services.postservices.model.Post;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    int save(MultipartFile[] multipartFile, String location, String caption, String userInfoId,List<String> tags) throws IOException;
    List<PostResponse> getPostsByPostIds(ProfilePostRequest profilePostRequest);
}
