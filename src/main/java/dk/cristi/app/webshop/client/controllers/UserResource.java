package dk.cristi.app.webshop.client.controllers;

import dk.cristi.app.webshop.client.services.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = {"user"})
@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public UserResource(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @ApiOperation("Get a unique token for anonymous users.")
    @GetMapping("/anonymous/key")
    public ResponseEntity<?> createCartKey() {
        String key = createKey();

        while (shoppingCartService.keyIsInUse(key)) {
            key = createKey();
        }

        Map<String, String> map = new HashMap<>(1);
        map.put("key", key);
        return ResponseEntity.ok(map);
    }

    private String createKey() {
        return RandomStringUtils.random(32, true, true);
    }
}
