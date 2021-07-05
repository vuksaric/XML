package services.authservices.controller;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.authservices.model.UserInfo;
import services.authservices.model.dto.AuthDTO;
import services.authservices.model.dto.RegistrationDTO;
import services.authservices.model.dto.UserResponseDTO;
import services.authservices.service.IAuthService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody RegistrationDTO registrationDTO){
        try{
            return new ResponseEntity(authService.registration(registrationDTO), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthDTO authDTO){
        try{
            return new ResponseEntity(authService.login(authDTO), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getUserInfoId/{username}")
    public ResponseEntity getByUsername(@PathVariable String username){
        try{
            return new ResponseEntity(authService.getByUsername(username), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity getById(@PathVariable String id){
        try{
            int intId = Integer.parseInt(id);
            return new ResponseEntity(authService.getById(intId), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/getUserInfoByUsername/{username}")
    public ResponseEntity getUserInfoByUsername(@PathVariable String username){
        try{
            return new ResponseEntity(authService.getUserInfoByUsername(username), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/checkUsername/{username}")
    public ResponseEntity checkUsername(@PathVariable String username){
        try{
            return new ResponseEntity(authService.checkUsername(username), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity edit(@RequestBody UserInfo userInfo) {
        try {
            return new ResponseEntity(authService.edit(userInfo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUsername/{id}")
    public String getUsername(@PathVariable int id){
        return authService.getUsername(id);
    }

    @PostMapping("/getUserInfoIds")
    public List<Integer> getUserInfoIds(@RequestBody List<String> usernames){
        return authService.getUserInfoIds(usernames);
    }
}
