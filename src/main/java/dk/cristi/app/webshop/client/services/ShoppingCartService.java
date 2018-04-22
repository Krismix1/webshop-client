package dk.cristi.app.webshop.client.services;

import dk.cristi.app.webshop.client.models.domain.ShoppingCart;
import dk.cristi.app.webshop.client.models.domain.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartService {
    private final Map<String, ShoppingCart> shoppingCartMap = new HashMap<>();

    public List<ShoppingCartItem> getItems(String uid) {
        final ShoppingCart shoppingCart = shoppingCartMap.get(uid);
        if (shoppingCart == null) {
            return new ArrayList<>();
        }
        return shoppingCart.getItems();
    }

    public void saveCart(String uid, ShoppingCart cart) {
        shoppingCartMap.put(uid, cart);
    }

    public boolean keyIsInUse(String key) {
        return shoppingCartMap.containsKey(key);
    }
}
