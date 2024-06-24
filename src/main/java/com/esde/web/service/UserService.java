package com.esde.web.service;

import com.esde.web.model.User;
import com.esde.web.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    void createUser(User user) throws ServiceException;

    void updateUsername(String email, String username) throws ServiceException;

    void verifyUser(String email) throws ServiceException;

    boolean userVerified(String email) throws ServiceException;

    void deleteUser(String email) throws ServiceException;

    User login(String email, String password) throws ServiceException;
}