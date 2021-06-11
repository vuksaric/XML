package services.picturevideoservices.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.picturevideoservices.model.Image;
import services.picturevideoservices.repository.ImageDbRepository;
import services.picturevideoservices.service.implementation.FileLocationService;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final FileLocationService fileLocationService;

    public ImageController(FileLocationService fileLocationService){this.fileLocationService = fileLocationService;}

    @PostMapping("/upload")
    Integer uploadImage(@RequestParam("file") MultipartFile multipartImage) throws Exception {
        Image dbImage = new Image();
        dbImage.setName(multipartImage.getName());
        dbImage.setContent(multipartImage.getBytes());

        return fileLocationService.save(multipartImage.getBytes(),multipartImage.getOriginalFilename());
    }
}
