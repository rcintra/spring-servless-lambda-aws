package com.rcintra.customer;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final JsonPlaceHolderService jsonPlaceHolderService;

    private List<Customer> customers = new ArrayList<>();

    public CustomerController(JsonPlaceHolderService jsonPlaceHolderService) {
        this.jsonPlaceHolderService = jsonPlaceHolderService;
    }

    @GetMapping
    List<Customer> findAll() {
        return customers;
    }

    @GetMapping("/{id}")
    Optional<Customer> findById(@PathVariable Integer id) {
        return Optional.ofNullable(customers
                .stream()
                .filter(customer -> customer.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id: " + id + " not found")));
    }

    @PostConstruct
    private void init() {
        if (customers.isEmpty()) {
            log.info("Loading customers from jsonplaceholder.typicode.com");
            customers = jsonPlaceHolderService.loadCustomers();
        }
    }
}
