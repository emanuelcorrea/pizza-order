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

    @Operation(summary = "Retrieve a list of customers", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of customers")
    })
    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        return ResponseEntity.ok(customerList);
    }

    @Operation(summary = "Retrieve a single customer by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> find(@PathVariable String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(UUID.fromString(id));

        return optionalCustomer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new customer", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new customer"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid customer data")
    })
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody @Valid CustomerDto customerRecord) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRecord, customer);

        return ResponseEntity.ok(customerRepository.save(customer));
    }

    @Operation(summary = "Delete a customer by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
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
