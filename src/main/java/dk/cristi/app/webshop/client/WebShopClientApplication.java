package dk.cristi.app.webshop.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
@EnableScheduling
@EnableEurekaClient
public class WebShopClientApplication {

	@RequestMapping("/")
	public String index() {
		return "index.html";
	}

	public static void main(String[] args) {
		SpringApplication.run(WebShopClientApplication.class, args);
	}
}
