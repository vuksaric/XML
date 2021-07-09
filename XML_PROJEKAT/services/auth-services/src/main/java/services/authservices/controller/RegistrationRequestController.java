package services.authservices.controller;

import org.springframework.web.bind.annotation.*;
import services.authservices.dto.RegistrationRequestDTO;
import services.authservices.model.RegistrationRequest;
import services.authservices.model.UserInfo;
import services.authservices.service.implementation.RegistrationRequestService;

import java.util.List;

@RestController
@RequestMapping("/auth/registrationRequest")
public class RegistrationRequestController {

    private final RegistrationRequestService registrationRequestService;

    public RegistrationRequestController(RegistrationRequestService registrationRequestService) {
        this.registrationRequestService = registrationRequestService;
    }

    @PostMapping("/new")
    public void newRequest(@RequestBody UserInfo userInfo) {
         registrationRequestService.newRequest(userInfo);
    }

    @GetMapping("/getAll")
    public List<RegistrationRequestDTO> getAllForProfile()
    {
        return registrationRequestService.getAll();
    }

    @PutMapping("/delete/{id}")
    public void delete(@PathVariable int id)
    {
        registrationRequestService.delete(id);
    }

    @PutMapping("/approve/{id}")
    public void approve(@PathVariable int id)
    {
        registrationRequestService.approve(id);
    }
}
