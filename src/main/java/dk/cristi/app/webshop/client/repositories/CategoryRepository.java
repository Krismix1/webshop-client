package dk.cristi.app.webshop.client.repositories;

import dk.cristi.app.webshop.client.models.entities.Category;
import org.springframework.data.repository.CrudRepository;


public interface CategoryRepository extends CrudRepository<Category, Long> {
}
