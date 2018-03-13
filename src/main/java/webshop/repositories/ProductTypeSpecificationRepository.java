package webshop.repositories;

import org.springframework.data.repository.CrudRepository;
import webshop.models.entities.ProductTypeSpecification;

public interface ProductTypeSpecificationRepository extends CrudRepository<ProductTypeSpecification, Long> {
}
