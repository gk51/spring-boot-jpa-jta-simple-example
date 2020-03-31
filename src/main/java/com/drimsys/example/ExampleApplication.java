package com.drimsys.example;

import com.drimsys.example.db.ApiService;
import com.drimsys.example.db.h2.Customer;
import com.drimsys.example.db.mysql.Person;
import com.drimsys.example.db.oracle.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

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

        User oracle = User.builder().name("Test oracle").age(1).build();
        Customer h2 = Customer.builder().name("Test h2").age(1).build();
        Person mysql = Person.builder().name("Test mysql").age(1).build();

        // 첫번째 insert 에서 Error 발생 (Oracle)
        try {
            apiService.addObjectRollbackFromOracle(oracle, h2, mysql);
        } catch (RuntimeException e1) {
            ExampleApplication.log.info("Rollback error test");
            print();

            sleep(5000);
            // 두번째 insert 에서 Error 발생 (H2)
            try {
                apiService.addObjectRollbackFromH2(oracle, h2, mysql);
            } catch (RuntimeException e2) {
                ExampleApplication.log.info("Rollback error test");
                print();

                sleep(5000);
                // 세번째 insert 에서 Error 발생 (Mysql)
                try {
                    apiService.addObjectRollbackFromMysql(oracle, h2, mysql);
                } catch (RuntimeException e3) {
                    ExampleApplication.log.info("Rollback error test");
                    print();
                }
            }
        }

        sleep(5000);
        // 정상 insert
        apiService.addObject(oracle, h2, mysql);
        print();

        System.exit(0);
    }

    public void print() {
        ExampleApplication.log.info("========================================");
        Map<String, Object> result = apiService.findAll();
        ExampleApplication.log.info("User >> " + result.get("user").toString());
        ExampleApplication.log.info("Person >> " + result.get("person").toString());
        ExampleApplication.log.info("Customer >> " + result.get("customer").toString());
        ExampleApplication.log.info("========================================");
    }

    public void sleep(long time) {
        try { Thread.sleep(time); } catch (Exception e) {}
    }
}
