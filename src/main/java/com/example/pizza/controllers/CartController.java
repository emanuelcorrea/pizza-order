package com.example.pizza.controllers;

import com.example.pizza.dtos.CartDto;
import com.example.pizza.exceptions.ProductsNotFoundException;
import com.example.pizza.models.Cart;
import com.example.pizza.models.CartItem;
import com.example.pizza.models.Product;
import com.example.pizza.services.CartItemService;
import com.example.pizza.services.CartService;
import com.example.pizza.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/carts", produces = "application/json")
@Tag(name = "Carts", description = "Operations related to Carts")
public class CartController {
    private final CartService cartService;
    private final CustomerService customerService;
    private final CartItemService cartItemService;

    public CartController(CartService cartService, CartItemService cartItemService, CustomerService customerService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.customerService = customerService;
    }

    @Operation(summary = "Retrieve a list of carts", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of carts"),
    })
    @GetMapping
    public ResponseEntity<List<Cart>> findAll() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @Operation(summary = "Retrieve a single cart by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cart> findOne(@PathVariable String id) {
        UUID cartId = UUID.fromString(id);

        return cartService.findOne(cartId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a cart by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable String id) {
        UUID cartId = UUID.fromString(id);

        return cartService.findOne(cartId)
                .map(cart -> {
                    cartService.remove(cartId);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new cart", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new cart"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<Cart> create(@RequestBody @Valid @NotNull CartDto record) {
        return customerService.findOne(record.customer_id())
                .map(customer -> {
                    List<Product> products = new ArrayList<>(record.items());
                    Cart cart = cartService.create(customer, products);
                    return ResponseEntity.status(HttpStatus.CREATED).body(cart);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Retrieve a list of items by cart ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of items"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{id}/items")
    public ResponseEntity<List<CartItem>> findAllItems(@PathVariable String id) {
        return cartService.findOne(UUID.fromString(id))
                .map(cart -> ResponseEntity.ok(cart.getItems()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a list of items for a cart", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created items for the cart"),
            @ApiResponse(responseCode = "400", description = "Bad request - No items to insert in cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found"),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    @PostMapping("/{id}/items")
    @Transactional
    public ResponseEntity<Object> createItem(@PathVariable String id, @RequestBody List<Product> products) {
        try {
            Optional<Cart> optionalCart = cartService.findOne(UUID.fromString(id));

            if (optionalCart.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            if (products.isEmpty()) {
                return ResponseEntity.badRequest().body("No available items to insert in cart");
            }

            Cart cart = optionalCart.get();
            List<CartItem> cartItems = cartItemService.create(products, cart);

            return ResponseEntity.status(HttpStatus.CREATED).body(cartItems);
        } catch (ProductsNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
