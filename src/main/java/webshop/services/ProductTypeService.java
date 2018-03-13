package webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import webshop.models.entities.ProductType;
import webshop.repositories.ProductTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public Collection<ProductType> fetchAll() {
        return (Collection<ProductType>) productTypeRepository.findAll();
    }

    public Optional<ProductType> findByName(@NonNull String name) {
        return Optional.ofNullable(productTypeRepository.findByName(name));
    }
}