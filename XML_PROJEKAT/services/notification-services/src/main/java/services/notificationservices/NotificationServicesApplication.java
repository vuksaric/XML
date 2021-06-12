package services.notificationservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class NotificationServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServicesApplication.class, args);
	}

}
