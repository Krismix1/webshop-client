package webshop.repositories;

import org.springframework.data.repository.CrudRepository;
import webshop.models.entities.ProductType;

public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {
    ProductType findByName(String name);
}
