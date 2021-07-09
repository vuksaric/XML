package services.postservices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import services.postservices.dto.ImageDTO;

@FeignClient(name="pictureVideo",url="${app.pictureVideo.url}")
public interface PictureVideoClient {
    @PostMapping("/picture-video/upload")
    Integer uploadImage(@RequestBody ImageDTO imageDTO);
    @PostMapping("/picture-video/getById")
    String getLocationById(@RequestBody int id);
    @PostMapping("/picture-video/getImageById")
    boolean getImageById(@RequestBody int id);
}
