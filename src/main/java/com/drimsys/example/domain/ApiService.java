package com.drimsys.example.domain;

import com.drimsys.example.domain.h2.Customer;
import com.drimsys.example.domain.h2.CustomerService;
import com.drimsys.example.domain.oracle.User;
import com.drimsys.example.domain.oracle.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiService {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "multiTxManager")
    public void addObject(User user, Customer customer) {
        userService.addUser(user);
        customerService.addCustomer(customer);
    }

    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "multiTxManager")
    public void addObjectWithException(User user, Customer customer) {
        userService.addUser(user);
        customerService.addCustomer(customer);
        throw new RuntimeException("Error Test");
    }

    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "multiTxManager")
    public void deleteAll() {
        userService.deleteAllUser();
        customerService.deleteAllCustomer();
    }

    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "multiTxManager")
    public void deleteAllWithException() {
        userService.deleteAllUser();
        customerService.deleteAllCustomer();
        throw new RuntimeException("Error Test");
    }
}
