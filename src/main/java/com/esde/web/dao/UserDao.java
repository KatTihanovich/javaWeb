package com.esde.web.dao;

import com.esde.web.dao.exception.DaoException;
import com.esde.web.model.User;

import java.util.List;

public interface UserDao {
    User findByEmail(String email) throws DaoException;

    List<User> findAll() throws DaoException;

    void create(User user) throws DaoException;

    void updateUsername(String email, String username) throws DaoException;

    void verifyUser(String email) throws DaoException;

    boolean userVerified(String email) throws DaoException;

    void delete(String email) throws DaoException;
}