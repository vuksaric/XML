package services.profileservices.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import services.profileservices.client.PictureVideoClient;
import services.profileservices.dto.ImageDTO;
import services.profileservices.model.Profile;
import services.profileservices.model.ProfileCategory;
import services.profileservices.model.VerificationRequest;
import services.profileservices.repository.VerificationRequestRepository;
import services.profileservices.service.IVerificationRequestService;

import java.io.IOException;
import java.util.List;

@Service
public class VerificationRequestService implements IVerificationRequestService {
    private final VerificationRequestRepository verificationRequestRepository;
    private final PictureVideoClient pictureVideoClient;
    private final ProfileService profileService;

    public VerificationRequestService(VerificationRequestRepository verificationRequestRepository, PictureVideoClient pictureVideoClient, ProfileService profileService){
        this.verificationRequestRepository=verificationRequestRepository;
        this.pictureVideoClient = pictureVideoClient;
        this.profileService = profileService;
    }

    @Override
    public int save(MultipartFile multipartFile, String name, String surname, ProfileCategory category, String profileId) throws IOException {
        int officialDocument = pictureVideoClient.uploadImage(new ImageDTO(multipartFile.getOriginalFilename(),multipartFile.getBytes()));
        int id = Integer.parseInt(profileId);
        VerificationRequest verificationRequest = new VerificationRequest(name, surname, category, officialDocument, id);
        return verificationRequestRepository.save(verificationRequest).getId();
    }

    @Override
    public List<VerificationRequest> getAll() {
        return verificationRequestRepository.findAll();
    }

    @Override
    public VerificationRequest edit(int profileId) {
        VerificationRequest verificationRequest = verificationRequestRepository.findOneByProfileId(profileId);
        profileService.editCategory(profileId,verificationRequest.getCategory());
        verificationRequest.setConfirmed(true);
        verificationRequestRepository.save(verificationRequest);
        return verificationRequest;
    }
}
