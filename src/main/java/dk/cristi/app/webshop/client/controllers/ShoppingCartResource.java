package dk.cristi.app.webshop.client.controllers;

import dk.cristi.app.webshop.client.controllers.http_exceptions.Http404Exception;
import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.models.entities.ShoppingCartItem;
import dk.cristi.app.webshop.client.services.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"shopping-cart"})
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartResource {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartResource(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @ApiOperation("Get the shopping cart for the specified uid (user id).")
    @GetMapping("/{uid}/items")
    public List<ShoppingCartItem> getItems(@PathVariable("uid") String userId) {
        return shoppingCartService.getCart(userId).orElseThrow(Http404Exception::new).getItems(); // TODO: 15-May-18 Convert back to ShoppigCart
    }

    @ApiOperation("Save shopping cart linked to uid.")
    @PutMapping("/{uid}")
    public ResponseEntity<?> putItems(@PathVariable("uid") String userId,
                                      @Valid @RequestBody ShoppingCart cart) {
        cart.setUid(userId);
        shoppingCartService.saveCart(cart);
        return ResponseEntity.noContent().build();
    }
}
