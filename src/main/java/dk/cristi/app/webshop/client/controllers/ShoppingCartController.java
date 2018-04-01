package dk.cristi.app.webshop.client.controllers;

import dk.cristi.app.webshop.client.controllers.http_exceptions.Http404Exception;
import dk.cristi.app.webshop.client.models.domain.ShoppingCart;
import dk.cristi.app.webshop.client.models.domain.ShoppingCartItem;
import dk.cristi.app.webshop.client.services.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Api(tags = {"shopping-cart"})
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/items")
    @ApiOperation(value = "Get all items in the shopping cart.")
    public List<ShoppingCartItem> getItems() {
        return shoppingCartService.getItems();
    }

    @PutMapping
    @ApiOperation(value = "Add new item to the shopping cart.")
    public ResponseEntity<?> addItem(@Valid @RequestBody ShoppingCartItem item) {
        shoppingCartService.addItem(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{id}")
    @ApiOperation(value = "Remove item from shopping cart by id.")
    public ShoppingCartItem removeItem(@PathVariable("id") int id) {
        return shoppingCartService.removeItem(id).orElseThrow(Http404Exception::new);
    }
}
