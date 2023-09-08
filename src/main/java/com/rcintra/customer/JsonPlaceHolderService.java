package com.rcintra.customer;

import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface JsonPlaceHolderService {

    @GetExchange("/users")
    List<Customer> loadCustomers();

}
