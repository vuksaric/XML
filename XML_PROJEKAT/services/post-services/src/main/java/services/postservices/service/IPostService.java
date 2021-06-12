package services.postservices.service;

import org.springframework.web.multipart.MultipartFile;
import services.postservices.model.Post;

import java.io.IOException;

public interface IPostService {
    int save(MultipartFile multipartFile, String location, String caption, String userInfoId) throws IOException;
}
