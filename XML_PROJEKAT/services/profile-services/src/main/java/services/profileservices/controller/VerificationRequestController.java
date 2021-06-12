package services.profileservices.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import services.profileservices.model.ProfileCategory;
import services.profileservices.service.IVerificationRequestService;

@RestController
@RequestMapping("/verificationRequest")
public class VerificationRequestController {
    private final IVerificationRequestService verificationRequestService;

    public VerificationRequestController(IVerificationRequestService verificationRequestService){
        this.verificationRequestService = verificationRequestService;
    }

    @PostMapping("/create")
    public Integer createPost(@RequestParam("file") MultipartFile multipartFile, @RequestParam("name") String name, @RequestParam("surname") String surname, @RequestParam("category") String category) throws Exception{
        return verificationRequestService.save(multipartFile, name, surname, ProfileCategory.valueOf(category));
    }
}
