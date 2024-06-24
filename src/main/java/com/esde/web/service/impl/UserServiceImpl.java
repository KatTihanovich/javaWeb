package com.esde.web.service.impl;

import com.esde.web.dao.UserDao;
import com.esde.web.dao.exception.DaoException;
import com.esde.web.dao.impl.UserDaoImpl;
import com.esde.web.model.User;
import com.esde.web.service.UserService;
import com.esde.web.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();
    private static final Logger logger = LogManager.getLogger();

    public UserServiceImpl() {

    }

    @Override
    public User findUserByEmail(String email) throws ServiceException {
        User user = null;
        try {
            user = userDao.findByEmail(email);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        List<User> users = new ArrayList<>();
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public void createUser(User user) throws ServiceException {
        try {
            if (findUserByEmail(user.getEmail()) != null) {
                throw new ServiceException("User already exists");
            }
            userDao.create(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User login(String email, String password) throws ServiceException {
        User user = null;
        try {
            user = userDao.findByEmail(email);
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
            return null;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUsername(String email, String username) throws ServiceException {
        try {
            userDao.updateUsername(email, username);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void verifyUser(String email) throws ServiceException {
        try {
            userDao.verifyUser(email);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean userVerified(String email) throws ServiceException {
        boolean userVerified = false;
        try {
            userVerified = userDao.userVerified(email);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return userVerified;
    }

    @Override
    public void deleteUser(String email) throws ServiceException {
        try {
            userDao.delete(email);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}