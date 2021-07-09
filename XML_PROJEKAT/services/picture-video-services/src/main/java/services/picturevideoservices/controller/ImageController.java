package services.picturevideoservices.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.picturevideoservices.dto.ImageDTO;
import services.picturevideoservices.model.Image;
import services.picturevideoservices.repository.ImageDbRepository;
import services.picturevideoservices.service.implementation.FileLocationService;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/picture-video")
public class ImageController {

    private final FileLocationService fileLocationService;

    public ImageController(FileLocationService fileLocationService){this.fileLocationService = fileLocationService;}

    @PostMapping("/upload")
    Integer uploadImage(@RequestBody ImageDTO imageDTO) throws Exception {
        return fileLocationService.save(imageDTO.getContent(),imageDTO.getName(), imageDTO.isImage());
    }

    @PostMapping("/getById")
    public String getLocationById(@RequestBody int id){
        return fileLocationService.getLocationById(id);
    }

    @PostMapping("/getImageById")
    public Boolean getImageById(@RequestBody int id){
        return fileLocationService.getImageById(id);
    }
}
