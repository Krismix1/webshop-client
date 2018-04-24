package dk.cristi.app.webshop.client.models.domain;

import dk.cristi.app.webshop.management.models.entities.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ShoppingCartItem {

    @Min(value = 1, message = "Minimum quantity for a shop cart item is 1.")
    private int quantity;
    @NotNull(message = "Product of cart item can't be null.")
    private Product product;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
