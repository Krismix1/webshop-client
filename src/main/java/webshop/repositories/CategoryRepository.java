package webshop.repositories;

import org.springframework.data.repository.CrudRepository;
import webshop.models.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
