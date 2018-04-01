package dk.cristi.app.webshop.client.models.domain;

import java.util.List;

public class ShoppingCart {

    private List<ShoppingCartItem> items;

    public ShoppingCart(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public boolean addItem(ShoppingCartItem item) {
        return items.add(item);
    }

    public boolean removeItem(ShoppingCartItem item) {
        return items.remove(item);
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }
}
