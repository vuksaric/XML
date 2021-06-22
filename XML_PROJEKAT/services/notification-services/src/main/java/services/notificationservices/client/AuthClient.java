package services.notificationservices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="auth",url="${app.auth.url}")
public interface AuthClient {
    @GetMapping("/auth/getUsername/{userInfoId}")
    String getUsername(@PathVariable int userInfoId);
}
