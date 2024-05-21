package com.spacest.bookmarkdemo.service;

import com.spacest.bookmarkdemo.entity.User;

public interface UserService extends EntityService<User>{
    User findByEmail(String email);
}
