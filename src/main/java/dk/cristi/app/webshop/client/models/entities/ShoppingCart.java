package dk.cristi.app.webshop.client.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.cristi.app.webshop.client.converters.LocalDateTimeAttributeConverter;
import dk.cristi.app.webshop.client.converters.LongTimestampDeserializer;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carts")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @NotNull(message = "Items of shopping cart must not be null.")
    @Valid
    @OneToMany
    private List<ShoppingCartItem> items;
    @JsonIgnore
    private String key;
    @Convert(converter = LocalDateTimeAttributeConverter.class) // TODO: 15-May-18 Look up how to register a converter
    @JsonDeserialize(using = LongTimestampDeserializer.class)
    private LocalDateTime registeredAt;

    // @formatter:off
    protected ShoppingCart() {}
    // @formatter:on

    public ShoppingCart(String key) {
        this.key = key;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}
