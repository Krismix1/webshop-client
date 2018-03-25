package webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webshop.controllers.http_exceptions.Http404Exception;
import webshop.models.domain.ProductTypeVO;
import webshop.models.entities.ProductType;
import webshop.models.entities.ProductTypeSpecification;
import webshop.models.entities.ProductTypeSpecificationKey;
import webshop.services.ProductTypeService;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/products/types")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping
    public Collection<ProductTypeVO> fetchAll() {
        return productTypeService.fetchAll().stream()
                .map(ProductTypeService.mapToValueObject())
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    public ProductTypeVO fetchOne(@PathVariable("name") String name) {

        final Optional<ProductType> productTypeOptional = productTypeService.findByName(name);
        // map the entity to a VO if it is present
        // returns an empty Optional otherwise
        final Optional<ProductTypeVO> productTypeVOOptional = productTypeOptional.map(ProductTypeService.mapToValueObject());
        // return if present, otherwise return a 404
        return productTypeVOOptional.orElseThrow(Http404Exception::new);
    }

    @PostMapping
    // TODO: 20-Mar-18 Apply validation for the parameter
    public ResponseEntity<?> postProductType(@RequestBody ProductTypeVO productTypeVO) {

        final ProductType productType = new ProductType();
        productType.setName(productTypeVO.getName());
        productType.setDescription(productTypeVO.getDescription());

        final List<ProductTypeSpecification> productTypeSpecifications = Stream.of(productTypeVO.getSpecifications())
                .map(specification -> {
                    ProductTypeSpecificationKey key = new ProductTypeSpecificationKey();
                    key.setKeyName(specification.getKeyName());
                    key.setType(specification.getKeyType());

                    ProductTypeSpecification productTypeSpecification = new ProductTypeSpecification();
                    productTypeSpecification.setValue(specification.getValue());
                    productTypeSpecification.setProductTypeSpecificationKey(key);
                    productTypeSpecification.setProductType(productType);

                    return productTypeSpecification;
                })
                .collect(Collectors.toList());

        productType.setSpecifications(productTypeSpecifications);

        productTypeService.save(productType);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{name}").buildAndExpand(productType.getName()).toUri();

        return ResponseEntity.created(location).build();
    }
}
