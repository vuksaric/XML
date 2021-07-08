package services.authservices.service.implementation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import services.authservices.dto.RegistrationRequestDTO;
import services.authservices.model.RegistrationRequest;
import services.authservices.model.UserInfo;
import services.authservices.model.dto.RegistrationDTO;
import services.authservices.repository.RegistrationRequestRepository;
import services.authservices.service.IAuthService;
import services.authservices.service.IRegistrationRequestService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationRequestService implements IRegistrationRequestService {

    private final RegistrationRequestRepository registrationRequestRepository;
    private final IAuthService authService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationRequestService(RegistrationRequestRepository registrationRequestRepository, IAuthService authService, PasswordEncoder passwordEncoder) {
        this.registrationRequestRepository = registrationRequestRepository;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void newRequest(UserInfo userInfo) {
        RegistrationRequest request = new RegistrationRequest();
        request.setUserInfo(userInfo);
        request.getUserInfo().setPassword(passwordEncoder.encode(request.getUserInfo().getPassword()));
        registrationRequestRepository.save(request);
    }

    @Override
    public void delete(int id) {
        RegistrationRequest request = registrationRequestRepository.findOneById(id);
        registrationRequestRepository.delete(request);
    }



    @Override
    public List<RegistrationRequestDTO> getAll() {
        List<RegistrationRequest> requests = registrationRequestRepository.findAll();
        List<RegistrationRequestDTO> result = new ArrayList<>();
        for(RegistrationRequest request : requests)
        {
            result.add(new RegistrationRequestDTO(request));
        }
        return result;
    }

    @Override
    public void approve(int id) {
        RegistrationRequest request = registrationRequestRepository.findOneById(id);
        RegistrationDTO registrationDTO = new RegistrationDTO(request.getUserInfo());
        registrationRequestRepository.delete(request);
        authService.registration(registrationDTO);
    }
}
