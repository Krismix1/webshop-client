package dk.cristi.app.webshop.client.repositories;

import dk.cristi.app.webshop.client.models.entities.ProductType;
import org.springframework.data.repository.CrudRepository;

public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {
    ProductType findByName(String name);
}
