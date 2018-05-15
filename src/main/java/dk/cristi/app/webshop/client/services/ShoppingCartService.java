package dk.cristi.app.webshop.client.services;

import dk.cristi.app.webshop.client.models.entities.ShoppingCart;
import dk.cristi.app.webshop.client.models.entities.ShoppingCartItem;
import dk.cristi.app.webshop.client.repositories.ShoppingCartItemRepository;
import dk.cristi.app.webshop.client.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    @Value("${shoppingCart.maxTimeSpan}")
    private long maxTimeSpan;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    public Optional<ShoppingCart> getCart(String uid) {
        return shoppingCartRepository.findByKey(uid);
    }

    @Transactional
    public ShoppingCart saveCart(ShoppingCart cart) {

        // Remove the previous saved cart
        final Optional<ShoppingCart> shoppingCartOptional = deleteShoppingCartByKey(cart.getKey());// TODO: 15-May-18 Maybe use the object?
        // if present, reuse the old id
        shoppingCartOptional.ifPresent(shoppingCart -> cart.setId(shoppingCart.getId()));
        // Save the new cart
        shoppingCartItemRepository.saveAll(cart.getItems());
        return shoppingCartRepository.save(cart);
    }

    public boolean keyIsInUse(String key) {
        return StreamSupport.stream(shoppingCartRepository.findAll().spliterator(), false)
                .anyMatch(cart -> cart.getKey().equals(key));
    }

    @Transactional
    public List<ShoppingCart> cleanShoppingCarts() {
        final List<ShoppingCart> toDelete = StreamSupport.stream(shoppingCartRepository.findAll().spliterator(), false)
                .filter(cart -> cart.getRegisteredAt().plusMinutes(maxTimeSpan).isAfter(LocalDateTime.now())) // FIXME: 15-May-18 Timezones
                .collect(Collectors.toList());
        shoppingCartRepository.deleteAll(toDelete);
        final List<ShoppingCartItem> items = toDelete
                .stream()
                .flatMap(cart -> cart.getItems().stream())
                .collect(Collectors.toList());
        shoppingCartItemRepository.deleteAll(items);
        return toDelete;
    }

    @Transactional
    public Optional<ShoppingCart> deleteShoppingCartByKey(String key) {
        final Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findByKey(key);
        if (!shoppingCartOptional.isPresent()) {
            return shoppingCartOptional;
        }

        ShoppingCart shoppingCart = shoppingCartOptional.get();
        shoppingCartRepository.delete(shoppingCart);
        shoppingCartItemRepository.deleteAll(shoppingCart.getItems());
        return shoppingCartOptional;
    }
}
