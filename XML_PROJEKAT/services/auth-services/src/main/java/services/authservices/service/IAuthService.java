package services.authservices.service;

import services.authservices.model.UserInfo;
import services.authservices.model.dto.AuthDTO;
import services.authservices.model.dto.RegistrationDTO;
import services.authservices.model.dto.UserResponseDTO;

public interface IAuthService {

    UserResponseDTO login(AuthDTO authDTO);
    boolean registration(RegistrationDTO registrationDTO);
    int getByUsername(String username);
    UserInfo getById(int id);
    boolean checkUsername(String username);
    boolean edit(UserInfo userInfo);
}
