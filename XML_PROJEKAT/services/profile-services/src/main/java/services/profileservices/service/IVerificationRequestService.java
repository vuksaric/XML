package services.profileservices.service;

import org.springframework.web.multipart.MultipartFile;
import services.profileservices.model.ProfileCategory;

import java.io.IOException;

public interface IVerificationRequestService {
    int save(MultipartFile multipartFile, String name, String surname, ProfileCategory category) throws IOException;
}
