package dk.cristi.app.webshop.client.tasks;

import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.services.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShoppingCartTasks {
    private static final Logger log = LoggerFactory.getLogger(ShoppingCartTasks.class);
    @Value("${cleanup.delay}")
    private static final long delay = 30 * 60 * 1000;
    @Value("${cleanup.initialDelay}")
    private static final long initialDelay = 30 * 60 * 1000;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Scheduled(initialDelay = ShoppingCartTasks.initialDelay, fixedDelay = ShoppingCartTasks.delay)
    public void cleanCarts() {
        log.info("Starting clean up of carts");
        final List<ShoppingCart> shoppingCarts = shoppingCartService.cleanShoppingCarts();
        log.info("Deleted x carts".replace('x', Character.forDigit(shoppingCarts.size(), 10)));
    }
}
