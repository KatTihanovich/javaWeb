package com.esde.web.service.impl;

import com.esde.web.dao.UserDao;
import com.esde.web.dao.impl.UserDaoImpl;
import com.esde.web.model.User;
import com.esde.web.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void createUser(User user) {
        if (findUserByEmail(user.getEmail())!=null){
            throw new RuntimeException();
        }
        userDao.create(user);
    }

    @Override
    public User login(String email, String password) {
        User user = userDao.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public void updateUsername(String email, String username){
        userDao.updateUsername(email, username);
    }

    @Override
    public void verifyUser(String email){
        userDao.verifyUser(email);
    }

    @Override
    public boolean userVerified(String email){
        return userDao.userVerified(email);
    }

    @Override
    public void deleteUser(String email) {
        userDao.delete(email);
    }
}