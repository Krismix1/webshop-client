package webshop.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webshop.controllers.http_exceptions.Http404Exception;
import webshop.models.entities.Category;
import webshop.services.CategoryService;

import java.util.Collection;

@RestController
@RequestMapping("/api/categories")
@Api(tags = {"categories"})
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Get all categories.")
    public Collection<Category> fetchAll() {
        return categoryService.fetchAll();
    }

    @GetMapping(path = "{id}", produces = {"application/json"})
    @ApiOperation(value = "Get one category by id.")
    public Category fetchOne(@PathVariable("id") long id) {
        return categoryService.fetchOne(id).orElseThrow(Http404Exception::new);
    }
}
