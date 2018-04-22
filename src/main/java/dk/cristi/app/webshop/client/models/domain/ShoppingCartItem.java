package dk.cristi.app.webshop.client.models.domain;

import dk.cristi.app.webshop.management.models.entities.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ShoppingCartItem {

    private int quantity;
    private Product product;

    public int getQuantity() {
        return quantity;
    }

    @Min(value = 1, message = "Minimum quantity for a shop cart item is 1.")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    @NotNull(message = "Product must be provided for shop cart item.")
    public void setProduct(Product product) {
        this.product = product;
    }
}
