package dk.cristi.app.webshop.client.helpers;

import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.unit.services.ShoppingCartServiceTest;

public final class DummyTestData {

    public static ShoppingCart SHOPPING_CART() {
        return new ShoppingCart(ShoppingCartServiceTest.EXISTING_KEY);
    }
}
