package dk.cristi.app.webshop.client.unit.services;

import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.repositories.ShoppingCartItemRepository;
import dk.cristi.app.webshop.client.repositories.ShoppingCartRepository;
import dk.cristi.app.webshop.client.services.ShoppingCartService;
import dk.cristi.app.webshop.client.helpers.DummyTestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;
    @InjectMocks
    private ShoppingCartService shoppingCartService;

    public final static String EXISTING_KEY = "123456789";
    public final static String NONEXISTENT_KEY = "987654321";

    @Before
    public void setup() {
        when(shoppingCartRepository.findByKey(EXISTING_KEY))
                .thenReturn(Optional.of(new ShoppingCart(EXISTING_KEY)));

        when(shoppingCartRepository.findByKey(NONEXISTENT_KEY))
                .thenReturn(Optional.empty());
    }

    @Test
    public void getCart_Found() {
        Optional<ShoppingCart> optionalCart = shoppingCartService.getCart(EXISTING_KEY);
        assertNotNull("Should not be null", optionalCart);
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
    } // TODO: 02-Aug-18 Change `key` to `uid` because `key` is to ambiguous and doesn't mean the same

    @Test
    public void saveCart() {
        fail("Not implemented");
    }

    @Test
    public void keyIsInUse() {
        // Can find key
        // Cannot find key
        fail("Not implemented");
    }

    @Test
    public void cleanShoppingCarts() {
        fail("Not implemented");
    }

    @Test
    public void deleteShoppingCartByKey() {
        fail("Not implemented");
    }
}
