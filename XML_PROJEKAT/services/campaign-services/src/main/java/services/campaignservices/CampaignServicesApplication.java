package services.campaignservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CampaignServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampaignServicesApplication.class, args);
	}

}
