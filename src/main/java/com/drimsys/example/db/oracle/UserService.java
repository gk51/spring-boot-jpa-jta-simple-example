package com.drimsys.example.db.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) throws RuntimeException {
        userRepository.save(user);
    }

    public void addUser() throws RuntimeException {
        throw new RuntimeException("Rollback test");
    }

    public List<User> findAll() throws RuntimeException {
        return userRepository.findAll();
    }

    public void deleteAllUser() throws RuntimeException {
        userRepository.deleteAll();
    }
}
