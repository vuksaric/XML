package services.profileservices.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.profileservices.model.UserInfo;
import services.profileservices.service.UserService;

@RestController
@RequestMapping("/login")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){this.userService = userService;}

    @PostMapping("/registration")
    public Boolean registration(@RequestBody UserInfo userInfo){return userService.save(userInfo);}
}
