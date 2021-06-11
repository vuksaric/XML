package services.authservices.service.implementation;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import services.authservices.Token;
import services.authservices.client.ProfileClient;
import services.authservices.model.UserInfo;
import services.authservices.model.dto.AuthDTO;
import services.authservices.model.dto.RegistrationDTO;
import services.authservices.model.dto.UserResponseDTO;
import services.authservices.repository.AuthRepository;
import services.authservices.security.TokenUtils;
import services.authservices.service.IAuthService;

@Service
public class AuthService implements IAuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtils token;
    private final ProfileClient profileClient;

    @Autowired
    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, TokenUtils token, ProfileClient profileClient) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.token = token;
        this.profileClient = profileClient;
    }

    @Override
    public UserResponseDTO login(AuthDTO authDTO) {

        UserInfo user = authRepository.findOneByUsername(authDTO.getUsername());
        if (user == null) {
            return null;
        }
        String jwt = token.generateToken(user);
        int expiresIn = token.getEXPIRES_IN();

        UserResponseDTO userResponse = new UserResponseDTO(user,jwt);
        userResponse.setTokenExpiresIn(expiresIn);
        if(passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
            return userResponse;
        }
        else{
            return null;
        }
    }

    @Override
    public boolean registration(RegistrationDTO registrationDTO) {
        registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        UserInfo userInfo = new UserInfo(registrationDTO);
        authRepository.save(userInfo);
        UserInfo ui = authRepository.findOneByUsername(userInfo.getUsername());
        profileClient.createProfile(ui.getId());
        return true;
    }

    @Override
    public int getByUsername(String username) {
        UserInfo ui = authRepository.findOneByUsername(username);
        return ui.getId();
    }
}
