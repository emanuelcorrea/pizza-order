package com.example.pizza.services;

import com.example.pizza.dtos.CustomerDto;
import com.example.pizza.exceptions.customer.CustomerNotFoundException;
import com.example.pizza.models.Customer;
import com.example.pizza.repositories.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findOne(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(UUID.fromString(id));

        if (optionalCustomer.isEmpty())
            throw new CustomerNotFoundException(UUID.fromString(id));

        return optionalCustomer.get();
    }

    public Customer create(CustomerDto customerRecord) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRecord, customer);

        return customerRepository.save(customer);
    }

    public Customer update(String id, CustomerDto customerRecord) {
        Customer customer = findOne(id);
        BeanUtils.copyProperties(customerRecord, customer);

        return customerRepository.save(customer);
    }

    public void delete(String id) {
        Customer customer = findOne(id);

        customerRepository.delete(customer);
    }
}
