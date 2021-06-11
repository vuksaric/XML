package services.authservices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile", url = "${app.profile.url}")
public interface ProfileClient {
    @PostMapping("profile/createProfile/{userInfoId}")
    Boolean createProfile(@PathVariable int userInfoId);
}
