package dk.cristi.app.webshop.client.helpers;

import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.unit.services.ShoppingCartServiceTest;

import java.time.LocalDateTime;

public final class DummyTestData {

    public static ShoppingCart SHOPPING_CART() {
        ShoppingCart shoppingCart = new ShoppingCart(ShoppingCartServiceTest.EXISTING_UID);
        shoppingCart.setRegisteredAt(LocalDateTime.parse("2018-08-06T20:30:00"));
        return shoppingCart;
    }
}
