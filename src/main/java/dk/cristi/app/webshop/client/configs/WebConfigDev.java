package dk.cristi.app.webshop.client.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("dev")
@Configuration
public class WebConfigDev implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**"); // Allow CORS for Angular application run as a different application
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT", "POST", "GET", "OPTIONS", "DELETE").exposedHeaders("Authorization", "Content-Type");
    }
}
