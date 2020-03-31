package com.drimsys.example.db.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public void addPerson(Person person) throws RuntimeException {
        personRepository.save(person);
    }

    public void addPerson() throws RuntimeException {
        throw new RuntimeException("Rollback test");
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public void deleteAllPerson() throws RuntimeException {
        personRepository.deleteAll();
    }
}
