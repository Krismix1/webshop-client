package webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.controllers.http_exceptions.Http404Exception;
import webshop.models.domain.ProductTypeSpecificationVO;
import webshop.models.domain.ProductTypeVO;
import webshop.models.entities.ProductType;
import webshop.services.ProductTypeService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @PostMapping("/{name}")
    public ResponseEntity<?> postProductType(@PathVariable("name") String productTypeName,
                                             @RequestParam("desc") String description,
                                             @RequestBody ProductTypeSpecificationVO[] specifications) {

        return ResponseEntity.ok().build();
    }
}
