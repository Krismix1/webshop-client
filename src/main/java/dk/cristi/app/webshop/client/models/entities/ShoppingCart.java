package dk.cristi.app.webshop.client.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.cristi.app.webshop.client.converters.LocalDateTimeAttributeConverter;
import dk.cristi.app.webshop.client.converters.LongTimestampDeserializer;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
    private String uid;
    @Convert(converter = LocalDateTimeAttributeConverter.class) // TODO: 15-May-18 Look up how to register a converter
    @JsonDeserialize(using = LongTimestampDeserializer.class)
    private LocalDateTime registeredAt;

    // @formatter:off
    protected ShoppingCart() {}
    // @formatter:on

    public ShoppingCart(String uid) {
        this.uid = uid;
        items = new LinkedList<>(); // FIXME: 06-Aug-18 ???
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(uid, that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }
}
