package dk.cristi.app.webshop.client.configs;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
//@EnableDiscoveryClient
public class DiscoveryClientConfig {
}
