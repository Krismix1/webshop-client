package webshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.models.domain.ProductTypeSpecificationVO;
import webshop.models.entities.ProductType;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/products/types")
public class ProductTypeController {

    @GetMapping
    public Collection<ProductType> fetchAll() {
        return Collections.emptyList();
    }

    @GetMapping("/{name}")
    public ProductTypeSpecificationVO fetchOne(@PathVariable("name") String productType) {

        return null;
    }

    @PostMapping("/{name}")
    public ResponseEntity<?> postProductType(@PathVariable("name") String productTypeName,
                                             @RequestParam("desc") String description,
                                             @RequestBody ProductTypeSpecificationVO[] specifications) {

        return ResponseEntity.ok().build();
    }
}
