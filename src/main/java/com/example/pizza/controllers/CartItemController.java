package com.example.pizza.controllers;

import com.example.pizza.models.Cart;
import com.example.pizza.models.CartItem;
import com.example.pizza.models.Product;
import com.example.pizza.repositories.CartItemRepository;
import com.example.pizza.repositories.CartRepository;
import com.example.pizza.repositories.ProductRepository;
import com.example.pizza.services.CartItemService;
import com.example.pizza.services.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {


    private final CartService cartService;

    private final CartItemService cartItemService;

    public CartItemController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> findAll() {
        return ResponseEntity.ok(cartItemService.findAll());
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> create(@PathVariable String id, @RequestBody List<Product> records) {
        Optional<Cart> optionalCart = cartService.findOne(UUID.fromString(id));

        if (optionalCart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (records.isEmpty()) {
            return ResponseEntity.badRequest().body("No available items to insert in cart");
        }

        Cart cart = optionalCart.get();

//        List<CartItem> cartItems = cartItemService.create(records, cart);

//        return ResponseEntity.ok(cartItems);
        return ResponseEntity.ok().build();
    }
}
