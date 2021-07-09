package services.agentappservices.service.implementation;

import org.springframework.stereotype.Service;
import services.agentappservices.model.Image;
import services.agentappservices.repository.FileSystemRepository;
import services.agentappservices.repository.ImageRepository;

import java.util.Date;

@Service
public class FileLocationService {
    private final FileSystemRepository fileSystemRepository;
    private final ImageRepository imageRepository;

    public FileLocationService(FileSystemRepository fileSystemRepository, ImageRepository imageRepository){this.fileSystemRepository = fileSystemRepository; this.imageRepository = imageRepository;}

    public Integer save(byte[] bytes, String imageName) throws Exception {
        String location = fileSystemRepository.save(bytes, imageName);

        return imageRepository.save(new Image(new Date().getTime() + "-" + imageName, location)).getId();
    }

    public String getLocationById(int id){
        Image image = imageRepository.findOneById(id);
        return image.getLocation();
    }
}
