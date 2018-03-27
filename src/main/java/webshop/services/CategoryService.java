package webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.models.entities.Category;
import webshop.repositories.CategoryRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Collection<Category> fetchAll() {
        return (Collection<Category>) categoryRepository.findAll();
    }

    public Optional<Category> fetchOne(long id) {
        return categoryRepository.findById(id);
    }
}
