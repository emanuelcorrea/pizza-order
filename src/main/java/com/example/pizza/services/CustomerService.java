package com.example.pizza.services;

import com.example.pizza.models.Customer;
import com.example.pizza.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> findOne(String id) {
        return customerRepository.findById(UUID.fromString(id));
    }
}
