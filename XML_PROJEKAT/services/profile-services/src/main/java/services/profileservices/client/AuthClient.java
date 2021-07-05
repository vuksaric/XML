package services.profileservices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import services.profileservices.dto.ProfileDTO;
import services.profileservices.dto.ViewProfileDTO;

import java.util.List;

@FeignClient(name="auth",url="${app.auth.url}")
public interface AuthClient {
    @GetMapping("/auth/getById/{id}")
    ProfileDTO getUserInfo(@PathVariable("id") int id);

    @GetMapping("/auth/getUserInfoByUsername/{username}")
    ViewProfileDTO getUserInfoByUsername(@PathVariable("username") String username);

    @GetMapping("/auth/checkUsername/{username}")
    Boolean checkUsername(@PathVariable("username") String username);

    @PutMapping("/auth/edit")
    Boolean edit(@RequestBody ProfileDTO profileDTO);

    @GetMapping("/auth/getUsername/{id}")
    String getUsername(@PathVariable("id") int id);

    @GetMapping("/auth/getUserInfoIds")
    List<Integer> getUserInfoIds(@RequestBody List<String> usernames);

}
