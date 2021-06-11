package services.profileservices.controller;

import org.springframework.web.bind.annotation.*;
import services.profileservices.service.IProfileService;
import services.profileservices.service.implementation.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final IProfileService profileService;

    public ProfileController(IProfileService profileService){this.profileService = profileService;}

    @PostMapping("/createProfile/{userInfoId}")
    public Boolean createProfile(@PathVariable int userInfoId){
        return profileService.createProfile(userInfoId);
    }
}
