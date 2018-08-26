package dk.cristi.app.webshop.client.controllers;

import dk.cristi.app.webshop.client.services.ShoppingCartService;
import dk.cristi.app.webshop.client.utils.UIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final int MAX_RETRIES = 5;
    @Value("${cart.uid.length}")
    private int uidLength;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public UserResource(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @ApiOperation("Get a unique token for anonymous users.")
    @GetMapping("/anonymous/key")
    public ResponseEntity<?> createCartKey() {
        String uid = createUid();

        int retries = 0;
        while (shoppingCartService.uidIsInUse(uid) && retries < MAX_RETRIES) {
            uid = createUid();
            retries++;
        }

        Map<String, String> map = new HashMap<>(1);
        map.put("uid", uid);
        return ResponseEntity.ok(map);
    }

    private String createUid() {
        return UIDUtils.getRandomUid(uidLength);
    }
}
