package dk.cristi.app.webshop.client.unit_context.services;

import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.models.entities.ShoppingCartItem;
import dk.cristi.app.webshop.client.repositories.ShoppingCartItemRepository;
import dk.cristi.app.webshop.client.repositories.ShoppingCartRepository;
import dk.cristi.app.webshop.client.utils.CollectionsUtil;
import dk.cristi.app.webshop.client.utils.UIDUtils;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

/**
 * Suit of tests for checking different constraint for the shopping carts table, such as uid length and uniqueness, etc.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // better run each test in a clean state
public class ShoppingCartDDLAndDMLTests {

    @Value("${cart.uid.length}")
    private int uidLength;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Test
    public void whenRetrieveAll_thenOK() {
        Iterable<ShoppingCart> all = shoppingCartRepository.findAll();
        assertNotNull("Should not be null", all);
        assertEquals(1, CollectionsUtil.toList(all).size());
    }

    @Test
    public void whenKeyIsRepeated_thenError() {
        String uid = UIDUtils.getRandomUid(uidLength);
        ShoppingCart shoppingCart = new ShoppingCart(uid);
        ShoppingCart shoppingCart1 = new ShoppingCart(uid);
        shoppingCartRepository.save(shoppingCart);
        try {
            shoppingCartRepository.save(shoppingCart1);
            fail("Should've thrown unique key violation");
        } catch (DataIntegrityViolationException e) {
            assertNotNull(e);
            assertTrue("The error is related to uid", e.getMessage().contains(uid));
        }
    }

    @Test
    public void cartUidTooLong_thenError() {
        String uid = UIDUtils.getRandomUid(uidLength);
        ShoppingCart shoppingCart = new ShoppingCart(uid + '2');
        try {
            shoppingCartRepository.save(shoppingCart);
            fail("Should've thrown error for violating column length");
        } catch (DataIntegrityViolationException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void canRetrieveCartByUid() {
        String uid = UIDUtils.getRandomUid(uidLength);
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        ShoppingCart shoppingCart = new ShoppingCart(uid);
        shoppingCart.setRegisteredAt(time);

        shoppingCartRepository.save(shoppingCart);

        Optional<ShoppingCart> cartByUid = shoppingCartRepository.findByUid(uid);
        assertNotNull(cartByUid);
        assertTrue(cartByUid.isPresent());
        assertEquals("Should have same uid", uid, cartByUid.get().getUid());
    }

    @Test
    public void registerTimeIsConverted() {
        String uid = UIDUtils.getRandomUid(uidLength);
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        ShoppingCart shoppingCart = new ShoppingCart(uid);
        shoppingCart.setRegisteredAt(time);

        shoppingCartRepository.save(shoppingCart);

        Optional<ShoppingCart> cartByUid = shoppingCartRepository.findByUid(uid);
        assertEquals("Time should properly be converted without any loss", time, cartByUid.get().getRegisteredAt());
        assertEquals("Should have same uid", uid, cartByUid.get().getUid());
    }

    @Test
    public void saveCartItems_thenOK() {
        // Test is considered succeeded if no exception is thrown
        String uid = UIDUtils.getRandomUid(uidLength);
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        List<ShoppingCartItem> items = Arrays.asList(new ShoppingCartItem(12, 2), new ShoppingCartItem(3, 5));
        ShoppingCart shoppingCart = new ShoppingCart(uid);
        shoppingCart.setRegisteredAt(time);
        shoppingCart.setItems(items);

        shoppingCartItemRepository.saveAll(items);
        shoppingCartRepository.save(shoppingCart);
    }

    @Test
    public void retrieveCartItems_thenOK() {
        String uid = UIDUtils.getRandomUid(uidLength);
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        List<ShoppingCartItem> items = Arrays.asList(new ShoppingCartItem(12, 2), new ShoppingCartItem(3, 5));
        ShoppingCart shoppingCart = new ShoppingCart(uid);
        shoppingCart.setRegisteredAt(time);
        shoppingCart.setItems(items);

        shoppingCartItemRepository.saveAll(items);
        shoppingCartRepository.save(shoppingCart);

        ShoppingCart savedCart = shoppingCartRepository.findByUid(uid).get();
        assertEquals(items.size(), savedCart.getItems().size());
        Set<Long> products = items.stream().map(ShoppingCartItem::getProduct).collect(Collectors.toSet());
        Set<Long> savedProducts = savedCart.getItems().stream().map(ShoppingCartItem::getProduct).collect(Collectors.toSet());
        assertTrue("Should contain all and only the products specified", products.containsAll(savedProducts));
    }

    @Test
    public void whenSaveNullItems_thenError() {
        fail("Not implemented. Move to ShoppingCartServiceTest with context");
    }

    @Test
    public void whenSaveNullCollection_thenError() {
        fail("Not implemented. Move to ShoppingCartServiceTest with context");
    }

    @Test
    public void whenSaveEmptyCollection_thenOK() {
        fail("Not implemented. Move to ShoppingCartServiceTest with context");
    }
}
