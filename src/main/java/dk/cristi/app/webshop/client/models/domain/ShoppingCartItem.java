package dk.cristi.app.webshop.client.models.domain;

import javax.validation.constraints.Min;

public class ShoppingCartItem {

    private int id;
    @Min(value = 0, message = "Price for item cannot be negative.")
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
