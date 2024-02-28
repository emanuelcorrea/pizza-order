package com.example.pizza.controllers;

import com.example.pizza.dtos.CustomerDto;
import com.example.pizza.models.Customer;
import com.example.pizza.repositories.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/customers", produces = "application/json")
@Tag(name = "Customers", description = "Customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Operation(summary = "Retrieves a list of customers", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customerList = customerRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(customerList);
    }

    @Operation(summary = "Retrieves a single customer", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> find(@PathVariable String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(UUID.fromString(id));

        return optionalCustomer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Creates a customer", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody @Valid CustomerDto customerRecord) {
        Customer customer = new Customer();

        BeanUtils.copyProperties(customerRecord, customer);

        return ResponseEntity.ok(customerRepository.save(customer));
    }

    @Operation(summary = "Deletes a customer", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        UUID customerId = UUID.fromString(id);

        return customerRepository.findById(customerId)
            .map(customer -> {
                customerRepository.deleteById(customerId);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
