package services.postservices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="notification",url="${app.notification.url}")
public interface NotificationClient {
    @PostMapping("reportRequest/save/{postId}/{username}")
    void save(@PathVariable int postId, @PathVariable String username);

}
