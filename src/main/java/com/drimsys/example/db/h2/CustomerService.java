package com.drimsys.example.db.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public void addCustomer(Customer customer) throws RuntimeException {
        customerRepository.save(customer);
    }

    public void addCustomer() throws RuntimeException {
        throw new RuntimeException("Rollback test");
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public void deleteAllCustomer() throws RuntimeException {
        customerRepository.deleteAll();
    }
}
