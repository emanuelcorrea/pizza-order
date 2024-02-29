package com.example.pizza.controllers;

import com.example.pizza.models.Cart;
import com.example.pizza.models.CartItem;
import com.example.pizza.models.Product;
import com.example.pizza.repositories.CartItemRepository;
import com.example.pizza.repositories.CartRepository;
import com.example.pizza.repositories.ProductRepository;
import com.example.pizza.services.CartItemService;
import com.example.pizza.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Tag(name = "Cart Items", description = "Cart Items")
@RequestMapping("/cart-items")
public class CartItemController {


    private final CartService cartService;

    private final CartItemService cartItemService;

    public CartItemController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @Operation(summary = "Retrieve a list of cart items", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of cart items")
    })
    @GetMapping
    public ResponseEntity<List<CartItem>> findAll() {
        return ResponseEntity.ok(cartItemService.findAll());
    }

    @Operation(summary = "Create a cart item", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new cart item"),
            @ApiResponse(responseCode = "400", description = "Bad request - No items to insert in cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
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
        // Uncomment the following lines when the cartItemService.create method is implemented
        // List<CartItem> cartItems = cartItemService.create(records, cart);
        // return ResponseEntity.status(HttpStatus.CREATED).body(cartItems);

        // Temporary response for testing purposes
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
