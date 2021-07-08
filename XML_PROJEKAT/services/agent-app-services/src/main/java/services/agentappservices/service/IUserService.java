package services.agentappservices.service;

import services.agentappservices.model.User;
import services.agentappservices.model.dto.AuthDTO;
import services.agentappservices.model.dto.RegistrationDTO;
import services.agentappservices.model.dto.UserResponseDTO;

public interface IUserService {
    boolean registration(RegistrationDTO registrationDTO);
    User getByUsername(String username);
    UserResponseDTO login(AuthDTO authDTO);
}
