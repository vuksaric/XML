package services.campaignservices.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import services.campaignservices.model.Commercial;
import services.campaignservices.repository.CommercialRepository;
import services.campaignservices.service.ICommercialService;

import java.io.IOException;
import java.util.List;

@Service
public class CommercialService implements ICommercialService {

    private final CommercialRepository commercialRepository;

    public CommercialService(CommercialRepository commercialRepository) {
        this.commercialRepository = commercialRepository;
    }

    @Override
    public int newCommercial(int postId, String website) throws IOException {

        Commercial commercial = new Commercial();
        commercial.setClickCount(0);
        commercial.setPostId(postId);
        commercial.setLink(website);
        Commercial result = commercialRepository.save(commercial);
        return result.getId();
    }

    @Override
    public Commercial getById(int id) {
        return commercialRepository.findOneById(id);
    }
}
