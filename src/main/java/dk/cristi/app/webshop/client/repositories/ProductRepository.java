package dk.cristi.app.webshop.client.repositories;

import dk.cristi.app.webshop.client.models.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
