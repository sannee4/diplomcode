package com.urrsunn.visitstat.service.security;


import com.urrsunn.visitstat.entity.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
