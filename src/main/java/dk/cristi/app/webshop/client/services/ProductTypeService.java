package dk.cristi.app.webshop.client.services;

import dk.cristi.app.webshop.client.models.domain.ProductTypeSpecificationVO;
import dk.cristi.app.webshop.client.models.domain.ProductTypeVO;
import dk.cristi.app.webshop.client.models.entities.ProductType;
import dk.cristi.app.webshop.client.models.entities.ProductTypeSpecification;
import dk.cristi.app.webshop.client.models.entities.ProductTypeSpecificationKey;
import dk.cristi.app.webshop.client.repositories.ProductTypeRepository;
import dk.cristi.app.webshop.client.repositories.ProductTypeSpecificationKeyRepository;
import dk.cristi.app.webshop.client.repositories.ProductTypeSpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeSpecificationRepository productTypeSpecificationRepository;
    private final ProductTypeSpecificationKeyRepository productTypeSpecificationKeyRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository, ProductTypeSpecificationRepository productTypeSpecificationRepository, ProductTypeSpecificationKeyRepository productTypeSpecificationKeyRepository) {
        this.productTypeRepository = productTypeRepository;
        this.productTypeSpecificationRepository = productTypeSpecificationRepository;
        this.productTypeSpecificationKeyRepository = productTypeSpecificationKeyRepository;
    }

    public Collection<ProductType> fetchAll() {
        return (Collection<ProductType>) productTypeRepository.findAll();
    }

    @Transactional
    // FIXME: 20-Mar-18 Transactional doesn't have effect
    public ProductType save(ProductType productType) {
        // Save the keys to the database
        final List<ProductTypeSpecificationKey> keys = productType.getSpecifications()
                .stream()
                .map(ProductTypeSpecification::getProductTypeSpecificationKey)
                .collect(Collectors.toList());
        productTypeSpecificationKeyRepository.saveAll(keys);
        // Save the specification with the saved keys
        productTypeSpecificationRepository.saveAll(productType.getSpecifications());
        // Save the product type
        return productTypeRepository.save(productType);
    }

    public Optional<ProductType> findByName(@NonNull String name) {
        return Optional.ofNullable(productTypeRepository.findByName(name));
    }

    public static Function<ProductType, ProductTypeVO> mapToValueObject() {
        return productType -> {
            final ProductTypeSpecificationVO[] specifications = productType.getSpecifications()
                    .stream()
                    .map(specification -> {
                        final String value = specification.getValue();
                        final String keyName = specification.getProductTypeSpecificationKey().getKeyName();
                        final String type = specification.getProductTypeSpecificationKey().getType();

                        return new ProductTypeSpecificationVO(keyName, value, type);
                    }).toArray(ProductTypeSpecificationVO[]::new);

            return new ProductTypeVO(productType.getName(), productType.getDescription(), specifications);
        };
    }
}