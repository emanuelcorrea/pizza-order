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
@Tag(name = "Carts", description = "Carts")
public class CartController {
    private final CartService cartService;

    private final CustomerService customerService;

    private final CartItemService cartItemService;

    public CartController(CartService cartService, CartItemService cartItemService, CustomerService customerService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.customerService = customerService;
    }

    @Operation(summary = "Retrieves a list of carts", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @GetMapping
    public ResponseEntity<List<Cart>> findAll() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @Operation(summary = "Retrieves a single cart", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cart> findOne(@PathVariable String id) {
        UUID cartId = UUID.fromString(id);

        return cartService.findOne(cartId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletes a cart", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable String id) {
        UUID cartId = UUID.fromString(id);

        return cartService.findOne(cartId).map(cart -> {
            cartService.remove(cartId);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Creates a cart", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<Cart> create(@RequestBody @Valid @NotNull CartDto record) {
        return customerService.findOne(record.customer_id())
                .map(customer -> {
                    List<Product> products = new ArrayList<>(record.items());

                    Cart cart = cartService.create(customer, products);

                    return ResponseEntity.ok(cart);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Retrieves a list of items by cart", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @GetMapping("/{id}/items")
    public ResponseEntity<List<CartItem>> findAllItems(@PathVariable String id) {
        return cartService.findOne(UUID.fromString(id))
                .map(cart -> {
                List<CartItem> cartItems = cart.getItems();

                return ResponseEntity.ok(cartItems);
            }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Creates a list of items by cart", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
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

            return ResponseEntity.ok(cartItems);
        } catch (ProductsNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
