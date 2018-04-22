package dk.cristi.app.webshop.client.controllers;

import dk.cristi.app.webshop.client.models.domain.ShoppingCart;
import dk.cristi.app.webshop.client.models.domain.ShoppingCartItem;
import dk.cristi.app.webshop.client.services.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
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

    @GetMapping("/{uid}/items")
    @ApiOperation(value = "Get all items in the shopping cart.")
    public List<ShoppingCartItem> getItems(@PathVariable("uid") String userId) {
        return shoppingCartService.getItems(userId);
    }

    @PutMapping("/{uid}")
    @ApiOperation(value = "Save items to a shopping cart.")
    public ResponseEntity<?> putItems(@PathVariable("uid") String userId,
                                      @RequestBody @Valid ShoppingCartItem[] items) {
        shoppingCartService.saveCart(userId, new ShoppingCart(Arrays.asList(items)));
        return ResponseEntity.noContent().build();
    }
}
