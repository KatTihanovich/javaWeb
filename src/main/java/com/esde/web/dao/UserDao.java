package com.esde.web.dao;

import com.esde.web.model.User;

import java.util.List;

public interface UserDao {
    User findByEmail(String email);

    List<User> findAll();

    void create(User user);

    void updateUsername(String email, String username);

    void verifyUser(String email);

    boolean userVerified(String email);

    void delete(String email);
}