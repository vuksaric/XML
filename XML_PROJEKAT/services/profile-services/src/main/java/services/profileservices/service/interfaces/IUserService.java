package services.profileservices.service.interfaces;

import org.springframework.stereotype.Service;
import services.profileservices.dto.UserInfoDTO;
import services.profileservices.model.UserInfo;

@Service
public interface IUserService {
    Boolean save(UserInfo userInfo);
}
