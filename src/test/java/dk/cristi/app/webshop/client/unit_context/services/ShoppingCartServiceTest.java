package dk.cristi.app.webshop.client.unit_context.services;

import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.models.entities.ShoppingCartItem;
import dk.cristi.app.webshop.client.services.ShoppingCartService;
import dk.cristi.app.webshop.client.utils.UIDUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ShoppingCartServiceTest {

    @Value("${cart.uid.length}")
    private int uidLength;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Test
    public void whenSaveNullItems_thenError() {
        String uid = UIDUtils.getRandomUid(uidLength);
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        List<ShoppingCartItem> items = Arrays.asList(new ShoppingCartItem(12, 2), null, new ShoppingCartItem(3, 5));
        ShoppingCart shoppingCart = new ShoppingCart(uid);
        shoppingCart.setRegisteredAt(time);
        shoppingCart.setItems(items);

        try {
            shoppingCartService.save(shoppingCart);
            fail("Should've thrown exception");
        } catch (NullPointerException e) {
            assertNotNull(e);
            assertFalse("Cart should not be saved", shoppingCartService.getCart(uid).isPresent());
        }
    }

    @Test
    public void whenSaveNullCollection_thenError() {
        String uid = UIDUtils.getRandomUid(uidLength);
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        ShoppingCart shoppingCart = new ShoppingCart(uid);
        shoppingCart.setRegisteredAt(time);
        shoppingCart.setItems(null);

        try {
            shoppingCartService.save(shoppingCart);
            fail("Should've thrown exception");
        } catch (NullPointerException e) {
            assertNotNull(e);
            assertFalse("Cart should not be saved", shoppingCartService.getCart(uid).isPresent());
        }
    }

    @Test
    public void whenSaveEmptyCollection_thenOK() {
        String uid = UIDUtils.getRandomUid(uidLength);
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        ShoppingCart shoppingCart = new ShoppingCart(uid);
        shoppingCart.setRegisteredAt(time);
        shoppingCart.setItems(Collections.emptyList());

        shoppingCartService.save(shoppingCart);
        assertTrue("Cart should be saved", shoppingCartService.getCart(uid).isPresent());
    }
}
