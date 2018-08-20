package dk.cristi.app.webshop.client.repositories;

import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUid(String uid);
}
