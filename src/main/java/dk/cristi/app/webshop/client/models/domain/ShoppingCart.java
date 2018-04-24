package dk.cristi.app.webshop.client.models.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ShoppingCart {

    @NotNull(message = "Items of shopping cart must not be null.")
    @Valid
    private List<ShoppingCartItem> items;

    // @formatter:off
    protected ShoppingCart() {}
    // @formatter:on

    public List<ShoppingCartItem> getItems() {
        return items;
    }
}
