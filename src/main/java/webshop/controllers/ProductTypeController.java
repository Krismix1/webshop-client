package webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.controllers.http_exceptions.Http404Exception;
import webshop.models.domain.ProductTypeSpecificationVO;
import webshop.models.entities.ProductType;
import webshop.services.ProductTypeService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/products/types")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping
    public Collection<ProductType> fetchAll() {
        return productTypeService.fetchAll();
    }

    @GetMapping("/{name}")
    public ProductType fetchOne(@PathVariable("name") String name) {

        final Optional<ProductType> productTypeOptional = productTypeService.findByName(name);
        // return if present, otherwise return a 404
        return productTypeOptional.orElseThrow(Http404Exception::new);
    }

    @PostMapping("/{name}")
    public ResponseEntity<?> postProductType(@PathVariable("name") String productTypeName,
                                             @RequestParam("desc") String description,
                                             @RequestBody ProductTypeSpecificationVO[] specifications) {

        return ResponseEntity.ok().build();
    }
}
