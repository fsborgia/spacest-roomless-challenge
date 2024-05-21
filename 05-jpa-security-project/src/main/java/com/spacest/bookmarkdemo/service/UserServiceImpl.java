package com.spacest.bookmarkdemo.service;

import com.spacest.bookmarkdemo.dao.UserRepository;
import com.spacest.bookmarkdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> res =  userRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            throw new RuntimeException("User id" + id + "not found");
        }
    }

    @Override
    public User save(User element) {
        return userRepository.save(element);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        User user =  userRepository.findByEmail(email);
        if (user != null) {
            return user;
        }
        else {
            throw new RuntimeException("User email" + email + "not found");
        }
    }
}
