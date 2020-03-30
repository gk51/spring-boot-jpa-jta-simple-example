package com.drimsys.example.domain.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteAllCustomer() {
        customerRepository.deleteAll();
    }
}
