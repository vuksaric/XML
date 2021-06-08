package services.profileservices.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import services.profileservices.model.UserInfo;
import services.profileservices.repository.UserRepository;
import services.profileservices.service.interfaces.IUserService;

@Service
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository){this.passwordEncoder = passwordEncoder; this.userRepository = userRepository;}

    @Override
    public Boolean save(UserInfo userInfo) {
        if (userRepository.findOneByUsername(userInfo.getUsername())!=null)
            return false;
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        if(userRepository.save(userInfo)!= null)
            return true;
        else return false;
    }
}
