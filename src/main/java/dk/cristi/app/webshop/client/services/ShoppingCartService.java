package dk.cristi.app.webshop.client.services;

import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.models.entities.ShoppingCartItem;
import dk.cristi.app.webshop.client.repositories.ShoppingCartItemRepository;
import dk.cristi.app.webshop.client.repositories.ShoppingCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    public Optional<ShoppingCart> getCart(String uid) {
        return shoppingCartRepository.findByUid(uid);
    }

    @Transactional
    public ShoppingCart saveCart(ShoppingCart cart) {

        // Remove the previous saved cart
        final Optional<ShoppingCart> shoppingCartOptional = deleteShoppingCartByKey(cart.getUid());
        // if present, reuse the old id
        shoppingCartOptional.ifPresent(shoppingCart -> cart.setId(shoppingCart.getId()));
        // Save the new cart
        shoppingCartItemRepository.saveAll(cart.getItems());
        return shoppingCartRepository.save(cart);
    }

    public boolean keyIsInUse(String key) {
        return StreamSupport.stream(shoppingCartRepository.findAll().spliterator(), false)
                .anyMatch(cart -> cart.getUid().equals(key));
    }

    /**
     * Deletes shopping carts which have been stored for the amount of minutes that is passed as a parameter.
     * @param span the duration in minutes of how long a shopping cart is allowed to be stored
     * @return the shopping carts that have been removed
     * */
    @Transactional
    public List<ShoppingCart> cleanShoppingCarts(long span) {
        if (span <= 0) {
            throw new IllegalArgumentException("Span must be a positive number");
        }
        final List<ShoppingCart> toDelete = StreamSupport.stream(shoppingCartRepository.findAll().spliterator(), false)
                // !isAfter(...) is used to also delete carts which result in registeredAt + span == now()
                // this is an edge case scenario, but still can be taken into consideration
                .filter(cart -> !cart.getRegisteredAt().plusMinutes(span).isAfter(LocalDateTime.now(ZoneOffset.UTC))) // FIXME: 15-May-18 Check timezones
                .collect(Collectors.toList());

        shoppingCartRepository.deleteAll(toDelete); // TODO: 02-Aug-18 Delete or mark as deleted?

        // Delete the items of the shopping carts
        final List<ShoppingCartItem> items = toDelete
                .stream()
                .flatMap(cart -> cart.getItems().stream())
                .collect(Collectors.toList());
        shoppingCartItemRepository.deleteAll(items);
        return toDelete;
    }

    @Transactional
    public Optional<ShoppingCart> deleteShoppingCartByKey(String key) {
        final Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findByUid(key);
        if (!shoppingCartOptional.isPresent()) {
            return shoppingCartOptional;
        }

        ShoppingCart shoppingCart = shoppingCartOptional.get();
        shoppingCartRepository.delete(shoppingCart);
        shoppingCartItemRepository.deleteAll(shoppingCart.getItems());
        return shoppingCartOptional;
    }
}
