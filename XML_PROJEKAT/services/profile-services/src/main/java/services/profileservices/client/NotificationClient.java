package services.profileservices.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name="notification",url="${app.notification.url}")
public interface NotificationClient {
    @PutMapping("/notification/followRequest/delete/{to}/{from}")
    void delete(@PathVariable int to, @PathVariable int from);

}
