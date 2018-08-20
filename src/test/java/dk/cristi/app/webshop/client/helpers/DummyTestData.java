package dk.cristi.app.webshop.client.helpers;

import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.unit.services.ShoppingCartServiceTest;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public final class DummyTestData {

    public static ShoppingCart SHOPPING_CART() {
        ShoppingCart shoppingCart = new ShoppingCart(ShoppingCartServiceTest.EXISTING_KEY);
        shoppingCart.setRegisteredAt(LocalDateTime.parse("2018-08-06T20:30:00"));
        return shoppingCart;
    }

    public static String getRandomCartUid() {
        return RandomStringUtils.random(32, true, true);
    }
}
