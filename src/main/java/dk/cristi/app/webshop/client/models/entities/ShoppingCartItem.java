package dk.cristi.app.webshop.client.models.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "cart_items")
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(value = 1, message = "Minimum quantity for a shop cart item is 1.")
    private int quantity;
    @Min(value = 1, message = "Product id of cart item can't be less than 1.")
    private long product;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
