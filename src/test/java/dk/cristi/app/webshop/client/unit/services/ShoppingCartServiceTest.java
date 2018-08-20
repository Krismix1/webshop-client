package dk.cristi.app.webshop.client.unit.services;

import dk.cristi.app.webshop.client.helpers.DummyTestData;
import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.models.entities.ShoppingCartItem;
import dk.cristi.app.webshop.client.repositories.ShoppingCartItemRepository;
import dk.cristi.app.webshop.client.repositories.ShoppingCartRepository;
import dk.cristi.app.webshop.client.services.ShoppingCartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;
    @InjectMocks
    private ShoppingCartService shoppingCartService;
    @Value("${shoppingCart.maxTimeSpan}")
    private long maxTimeSpan = 5;


    public final static String EXISTING_KEY = "123456789";
    public final static String NONEXISTENT_KEY = "987654321";

    @Before
    public void setup() {
        when(shoppingCartRepository.findByUid(EXISTING_KEY))
                .thenReturn(Optional.of(new ShoppingCart(EXISTING_KEY)));

        when(shoppingCartRepository.findByUid(NONEXISTENT_KEY))
                .thenReturn(Optional.empty());

        ShoppingCart shoppingCart = new ShoppingCart(DummyTestData.getRandomCartUid());
        shoppingCart.setRegisteredAt(LocalDateTime.parse("2017-08-16T13:00:30"));
        when(shoppingCartRepository.findAll())
                .thenReturn(Arrays.asList(DummyTestData.SHOPPING_CART(), shoppingCart));
    }

    @Test
    public void getCart_Found() {
        Optional<ShoppingCart> optionalCart = shoppingCartService.getCart(EXISTING_KEY);
        assertNotNull("Should not be null", optionalCart);
        assertTrue("Object should be present", optionalCart.isPresent());
        ShoppingCart shoppingCart = optionalCart.get();
        assertNotNull("Should not be null", shoppingCart);
        assertEquals("Should be equal objects", DummyTestData.SHOPPING_CART(), shoppingCart);
    }

    @Test
    public void getCart_NotFound() {
        Optional<ShoppingCart> optionalCart = shoppingCartService.getCart(NONEXISTENT_KEY);
        assertNotNull("Should not be null", optionalCart);
        assertFalse("Should be empty", optionalCart.isPresent());
        try {
            optionalCart.get();
            fail("Should've thrown exception");
        } catch (NoSuchElementException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void saveCart() {
        String uid = DummyTestData.getRandomCartUid();
        ShoppingCart shoppingCart = new ShoppingCart(uid);
        List<ShoppingCartItem> items = new ArrayList<>(3);
        items.add(new ShoppingCartItem(2, 3));
        items.add(new ShoppingCartItem(1, 1));
        items.add(new ShoppingCartItem(4, 3));
        shoppingCart.setItems(items);
        shoppingCart.setRegisteredAt(LocalDateTime.now(ZoneOffset.UTC));

        shoppingCartService.saveCart(shoppingCart);
        verify(shoppingCartRepository, times(1))
                .save(shoppingCart);

        verify(shoppingCartItemRepository, times(1))
                .saveAll(items);
    }

    @Test
    public void keyIsInUse() {
        // Can find uid
        assertTrue("Should return true if the uid is used", shoppingCartService.keyIsInUse(EXISTING_KEY));
        // Cannot find uid
        assertFalse("Should return false if the uid is not used", shoppingCartService.keyIsInUse(NONEXISTENT_KEY));
    }

    @Test
    public void cleanShoppingCarts() {
        // Create a shopping cart that should not be deleted
        ShoppingCart newShoppingCart = new ShoppingCart(DummyTestData.getRandomCartUid());
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        newShoppingCart.setRegisteredAt(now.minusMinutes(maxTimeSpan - 1)); // Leave 1 min till expiration

        // Create a shopping cart that should be deleted
        ShoppingCart cartToDelete = new ShoppingCart(DummyTestData.getRandomCartUid());
        cartToDelete.setRegisteredAt(now.plusMinutes(maxTimeSpan));

        Iterable<ShoppingCart> source = shoppingCartRepository.findAll();
        List<ShoppingCart> target = new ArrayList<>();
        source.forEach(target::add);
        target.add(newShoppingCart);
        target.add(cartToDelete);
        when(shoppingCartRepository.findAll())
                .thenReturn(target);

        // Finally test the method :)
        List<ShoppingCart> deletedCarts = shoppingCartService.cleanShoppingCarts(maxTimeSpan);

        assertTrue("At least the cart that was created in this test must be present", deletedCarts.size() > 0);
        for (ShoppingCart deletedCart : deletedCarts) {
            String message = String.format("Cart with uid %s is after %s", deletedCart.getUid(), deletedCart.getRegisteredAt().toString());
            assertTrue(message, deletedCart.getRegisteredAt().plusMinutes(maxTimeSpan).isBefore(now));
        }
        assertTrue("The newly registered shopping cart should not be part of deleted ones",
                deletedCarts.stream().noneMatch(cart -> cart.getUid().equals(newShoppingCart.getUid())));

        // no shopping carts in the storage
        when(shoppingCartRepository.findAll())
                .thenReturn(Collections.emptyList());
        deletedCarts = shoppingCartService.cleanShoppingCarts(1);
        assertEquals("No carts should be deleted", 0, deletedCarts.size());

        // only 1 cart in the storage, that should not be deleted
        when(shoppingCartRepository.findAll())
                .thenReturn(Collections.singletonList(newShoppingCart));
        deletedCarts = shoppingCartService.cleanShoppingCarts(maxTimeSpan);
        assertEquals("No carts should be deleted", 0, deletedCarts.size());

    }

    @Test
    public void deleteShoppingCartByKey_Found() {
        Optional<ShoppingCart> optionalCart = shoppingCartService.deleteShoppingCartByKey(EXISTING_KEY);
        assertNotNull("Should not be null", optionalCart);
        assertTrue("Object should be present", optionalCart.isPresent());
        ShoppingCart shoppingCart = optionalCart.get();
        assertNotNull("Should not be null", shoppingCart);
        assertEquals("Should be equal objects", DummyTestData.SHOPPING_CART(), shoppingCart);
        verify(shoppingCartRepository, times(1))
                .delete(optionalCart.get());
        verify(shoppingCartItemRepository, times(1))
                .deleteAll(any());
    }

    @Test
    public void deleteShoppingCartByKey_NotFound() {
        Optional<ShoppingCart> optionalCart = shoppingCartService.deleteShoppingCartByKey(NONEXISTENT_KEY);
        assertNotNull("Should not be null", optionalCart);
        assertFalse("Should be empty", optionalCart.isPresent());
        try {
            optionalCart.get();
            fail("Should've thrown exception");
        } catch (NoSuchElementException e) {
            assertNotNull(e);
            verify(shoppingCartRepository, times(0))
                    .delete(any(ShoppingCart.class));
            verify(shoppingCartItemRepository, times(0))
                    .deleteAll(any());
        }
    }
}
