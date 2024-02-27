package com.example.pizza.exceptions;

import org.hibernate.mapping.Join;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductsNotFoundException extends RuntimeException {
    private final Set<UUID> missingProducts;

    public ProductsNotFoundException(Set<UUID> missingProducts) {
        super("The following Products are not found: " +  missingProducts.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));

        System.out.println( missingProducts.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));

        this.missingProducts = missingProducts;
    }

    public Set<UUID> getMissingProducts() {
        return missingProducts;
    }
}
