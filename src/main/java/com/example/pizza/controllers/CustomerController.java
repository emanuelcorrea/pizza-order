package com.example.pizza.controllers;

import com.example.pizza.dtos.CustomerDto;
import com.example.pizza.models.Customer;
import com.example.pizza.repositories.CustomerRepository;
import com.example.pizza.services.CustomerService;
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

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Retrieve a list of customers", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of customers")
    })
    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customerList = customerService.findAll();

        return ResponseEntity.ok(customerList);
    }

    @Operation(summary = "Retrieve a single customer by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findOne(@PathVariable String id) {
        Customer customer = customerService.findOne(id);

        return ResponseEntity.ok(customer);
    }

    @Operation(summary = "Create a new customer", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new customer"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid customer data")
    })
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody @Valid CustomerDto customerRecord) {
        Customer createdCustomer = customerService.create(customerRecord);

        return ResponseEntity.ok(createdCustomer);
    }

    @Operation(summary = "Update a customer by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable String id, @RequestBody @Valid CustomerDto customerRecord) {
        Customer updatedCustomer = customerService.update(id, customerRecord);

        return ResponseEntity.ok(updatedCustomer);
    }

    @Operation(summary = "Delete a customer by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        customerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
