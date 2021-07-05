package services.postservices.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IStoryService {
    int save(MultipartFile[] multipartFile, String location, String caption,boolean closeFriends, boolean highlight, String userInfoId, List<String> tags) throws IOException;
}
