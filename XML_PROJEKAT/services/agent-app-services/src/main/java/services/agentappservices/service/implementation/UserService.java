package services.agentappservices.service.implementation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import services.agentappservices.model.Gender;
import services.agentappservices.model.User;
import services.agentappservices.model.dto.AuthDTO;
import services.agentappservices.model.dto.RegistrationDTO;
import services.agentappservices.model.dto.UserResponseDTO;
import services.agentappservices.repository.UserRepository;
import services.agentappservices.security.TokenUtils;
import services.agentappservices.service.IUserService;
import services.authservices.model.UserInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtils token;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenUtils token){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.token = token;
    }

    @Override
    public boolean registration(RegistrationDTO registrationDTO) {
        for(User u : userRepository.findAll()){
            if(u.getUsername().equals(registrationDTO.getUsername()))
                return false;
        }

        registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        if(registrationDTO.getWebsite()!= ""){
            registrationDTO.setAgent(true);
        }else
            registrationDTO.setAgent(false);
        User user = new User(registrationDTO);
        User response = userRepository.save(user);
        if(response != null)
            return true;
        else return false;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public UserResponseDTO login(AuthDTO authDTO) {
        User user = userRepository.findOneByUsername(authDTO.getUsername());
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
}
