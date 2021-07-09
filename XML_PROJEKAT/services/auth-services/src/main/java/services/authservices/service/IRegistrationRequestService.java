package services.authservices.service;

import services.authservices.dto.RegistrationRequestDTO;
import services.authservices.model.RegistrationRequest;
import services.authservices.model.UserInfo;

import java.util.List;

public interface IRegistrationRequestService {

    void newRequest(UserInfo userInfo);
    void delete(int id);
    List<RegistrationRequestDTO> getAll();
    void approve(int id);
}
