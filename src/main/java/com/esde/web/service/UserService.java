package com.esde.web.service;

import com.esde.web.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    List<User> getAllUsers();

    void createUser(User user);

    void updateUsername(String email, String username);

    void verifyUser(String email);

    boolean userVerified(String email);

    void deleteUser(String email);

    User login(String email, String password);
}