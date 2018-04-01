package dk.cristi.app.webshop.client.configs;

import dk.cristi.app.webshop.client.models.domain.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;

/**
 * Global configuration for the web shop. Contains essential information and beans for
 * a eCommerce platform.
 */
@Configuration
public class WebShopConfig {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ShoppingCart cart() {
        return new ShoppingCart(new LinkedList<>());
    }
}
