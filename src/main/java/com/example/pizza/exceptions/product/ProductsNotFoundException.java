package com.example.pizza.exceptions.product;

import com.example.pizza.interfaces.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class ProductsNotFoundException extends RuntimeException implements CustomException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    private final Set<UUID> missingProducts;

    public ProductsNotFoundException(Set<UUID> missingProducts) {
        super("The following Products are not found: " +  missingProducts.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));

        this.missingProducts = missingProducts;
    }
}
