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

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public UserInfo getById(int id) {
        UserInfo ui = authRepository.findOneById(id);
        return ui;
    }

    @Override
    public UserInfo getUserInfoByUsername(String username) {
        UserInfo ui = authRepository.findOneByUsername(username);
        return ui;
    }


    @Override
    public boolean checkUsername(String username) {
        UserInfo userInfo = authRepository.findOneByUsername(username);
        if(userInfo==null)
            return true;
        else
            return false;
    }

    @Override
    public boolean edit(UserInfo userInfo) {
        UserInfo oldUserInfo = authRepository.findOneById(userInfo.getId());
        oldUserInfo.setEmail(userInfo.getEmail());
        oldUserInfo.setGender(userInfo.getGender());
        oldUserInfo.setDateOfBirth(userInfo.getDateOfBirth());
        oldUserInfo.setPhone(userInfo.getPhone());
        oldUserInfo.setSurname(userInfo.getSurname());
        oldUserInfo.setName(userInfo.getName());
        oldUserInfo.setUsername(userInfo.getUsername());

        if (authRepository.save(oldUserInfo) != null)
            return true;
        else
            return false;
    }
    public String getUsername(int id) {
        UserInfo ui = authRepository.findOneById(id);
        return ui.getUsername();

    }

    @Override
    public List<Integer> getUserInfoIds(List<String> usernames) {
        List<Integer> result = new ArrayList<>();

        for(String username : usernames){
            result.add(authRepository.findOneByUsername(username).getId());
        }
        return result;
    }
}
