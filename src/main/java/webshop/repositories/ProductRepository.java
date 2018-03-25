package webshop.repositories;

import org.springframework.data.repository.CrudRepository;
import webshop.models.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
