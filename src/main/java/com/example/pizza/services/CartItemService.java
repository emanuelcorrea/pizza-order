package com.example.pizza.services;

import com.example.pizza.exceptions.product.ProductsNotFoundException;
import com.example.pizza.models.Cart;
import com.example.pizza.models.CartItem;
import com.example.pizza.models.Product;
import com.example.pizza.repositories.CartItemRepository;
import com.example.pizza.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    public Optional<CartItem> findOne(String id) {
        return cartItemRepository.findById(UUID.fromString(id));
    }

    @Transactional
    public List<CartItem> create(List<Product> products, Cart cart) {
        List<UUID> productIds = products.stream().map(Product::getId).toList();

        Map<UUID, Product> foundProducts = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        Set<UUID> missingProducts = productIds.stream()
                .filter(id -> !foundProducts.containsKey(id))
                .collect(Collectors.toSet());

        if (!missingProducts.isEmpty())
            throw new ProductsNotFoundException(missingProducts);

        List<CartItem> cartItems = products
                .stream()
                .map(product -> {
                    Product productCart = foundProducts.get(product.getId());

                    CartItem cartItem = new CartItem();

                    cartItem.setName(product.getTitle());
                    cartItem.setValue(product.getValue());
                    cartItem.setCart(cart);
                    cartItem.setProduct(productCart);

                    return cartItem;
                })
                .toList();

       return cartItemRepository.saveAll(cartItems);
    }
}
