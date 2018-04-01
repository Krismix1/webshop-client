package dk.cristi.app.webshop.client.services;

import dk.cristi.app.webshop.client.models.domain.ShoppingCart;
import dk.cristi.app.webshop.client.models.domain.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    private final ShoppingCart shoppingCart;

    @Autowired
    public ShoppingCartService(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void addItem(ShoppingCartItem item) {
        int lastId = shoppingCart.getItems()
                .stream()
                .mapToInt(ShoppingCartItem::getId)
                .max()
                .orElse(0);
        item.setId(lastId + 1);
        shoppingCart.addItem(item);
    }

    public List<ShoppingCartItem> getItems() {
        return shoppingCart.getItems();
    }

    public Optional<ShoppingCartItem> removeItem(int id) {
        // Find the item in the shopping cart
        Optional<ShoppingCartItem> itemOptional = getItems()
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst();
        // If found, try and remove it
        // If it can't be removed, then it is an unexpected behaviour
        if (itemOptional.isPresent()) {
            final boolean removed = shoppingCart.removeItem(itemOptional.get());
            if (removed) {
                return itemOptional;
            } else {
                throw new RuntimeException("Could not remove item from list");
            }
        } else {
            return Optional.empty();
        }
    }
}
