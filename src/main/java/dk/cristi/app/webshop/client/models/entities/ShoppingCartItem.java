package dk.cristi.app.webshop.client.models.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

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

    // @formatter:off
    protected ShoppingCartItem() {}
    // @formatter:on

    /** This class should only be instantiated by JPA, JSON Deserializer. This constructor is used for tests. */
    public ShoppingCartItem(long product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartItem item = (ShoppingCartItem) o;
        return quantity == item.quantity &&
                product == item.product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, product);
    }
}
