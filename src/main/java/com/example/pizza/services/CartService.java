package com.example.pizza.services;

import com.example.pizza.models.Cart;
import com.example.pizza.models.CartItem;
import com.example.pizza.models.Customer;
import com.example.pizza.models.Product;
import com.example.pizza.repositories.CartItemRepository;
import com.example.pizza.repositories.CartRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {
    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart create(Customer customer, List<Product> products) {
        Cart cart = new Cart();
        cart.setCustomer(customer);

        List<CartItem> cartItemList = new ArrayList<CartItem>();

        if (!products.isEmpty()) {
            for (Product product : products) {
                CartItem item = new CartItem();

                item.setName(product.getTitle());
                item.setValue(product.getValue());
                item.setProduct(product);
                item.setCart(cart);

                cartItemList.add(item);
            }

            cartItemRepository.saveAll(cartItemList);
            cart.setItems(cartItemList);
        }

        return cartRepository.save(cart);
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Optional<Cart> findOne(UUID id) {
        return cartRepository.findById(id);
    }

    public void remove(UUID id) {
       cartRepository.deleteById(id);
    }
}
