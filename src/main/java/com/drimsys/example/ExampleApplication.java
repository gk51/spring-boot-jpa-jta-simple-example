package com.drimsys.example;

import com.drimsys.example.domain.ApiService;
import com.drimsys.example.domain.h2.Customer;
import com.drimsys.example.domain.oracle.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedList;
import java.util.List;

@Log4j2
@SpringBootApplication
public class ExampleApplication implements CommandLineRunner {
    @Autowired
    private ApiService apiService;

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        apiService.deleteAll();

        List<User> users = new LinkedList<>();
        for(int i=0; i<10; i++) {
            users.add(
                    User.builder().name("test_"+i).age(i).build()
            );
        }

        List<Customer> customers = new LinkedList<>();
        for(int i=0; i<10; i++) {
            customers.add(
                    Customer.builder().name("test_"+i).age(i).build()
            );
        }

        // 홀수 : save 정상
        // 짝수 : Customer save >> throw RuntimeException
        for(int i=0; i<10; i++) {
            switch (i%2) {
                case 0:
                    apiService.addObject(users.get(i), customers.get(i));
                    break;
                case 1:
                    try {
                        apiService.addObjectWithException(users.get(i), customers.get(i));
                    } catch (Exception e) {
                        ExampleApplication.log.info("Error test : " + e.getMessage());
                    }
                    break;
            }
        }
    }
}
