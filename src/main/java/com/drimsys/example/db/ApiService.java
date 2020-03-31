package com.drimsys.example.db;

import com.drimsys.example.db.h2.Customer;
import com.drimsys.example.db.h2.CustomerService;
import com.drimsys.example.db.mysql.Person;
import com.drimsys.example.db.mysql.PersonService;
import com.drimsys.example.db.oracle.User;
import com.drimsys.example.db.oracle.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class ApiService {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PersonService personService;

    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "multiTxManager")
    public void addObject(User user, Customer customer, Person person) {
        userService.addUser(user);
        customerService.addCustomer(customer);
        personService.addPerson(person);
    }

    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "multiTxManager")
    public void addObjectRollbackFromOracle(User user, Customer customer, Person person) {
        userService.addUser();
        customerService.addCustomer(customer);
        personService.addPerson(person);
    }

    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "multiTxManager")
    public void addObjectRollbackFromH2(User user, Customer customer, Person person) {
        userService.addUser(user);
        customerService.addCustomer();
        personService.addPerson(person);
    }

    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "multiTxManager")
    public void addObjectRollbackFromMysql(User user, Customer customer, Person person) {
        userService.addUser(user);
        customerService.addCustomer(customer);
        personService.addPerson();
    }

    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "multiTxManager")
    public Map<String, Object> findAll() {
        Map<String, Object> result = new HashMap<>();
        result.put("user", userService.findAll());
        result.put("person", personService.findAll());
        result.put("customer", customerService.findAll());
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "multiTxManager")
    public void deleteAll() {
        userService.deleteAllUser();
        customerService.deleteAllCustomer();
        personService.deleteAllPerson();
    }
}
