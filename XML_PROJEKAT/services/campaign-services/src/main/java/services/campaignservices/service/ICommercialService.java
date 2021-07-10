package services.campaignservices.service;

import org.springframework.web.multipart.MultipartFile;
import services.campaignservices.model.Commercial;

import java.io.IOException;
import java.util.List;

public interface ICommercialService {

    int newCommercial(int postId, String website) throws IOException;
    Commercial getById(int id);
}
