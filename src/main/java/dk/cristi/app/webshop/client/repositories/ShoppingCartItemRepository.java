package dk.cristi.app.webshop.client.repositories;

import dk.cristi.app.webshop.client.models.entities.ShoppingCartItem;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem, Long> {
}
