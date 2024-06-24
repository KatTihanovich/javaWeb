package com.esde.web.dao.impl;

import com.esde.web.dao.UserDao;
import com.esde.web.dao.exception.DaoException;
import com.esde.web.model.User;
import com.esde.web.pool.DatabasePool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();

    private final static String DELETE_USER = "DELETE FROM kat_users WHERE email = ?";
    private final static String SELECT_VERIFIED = "SELECT verified FROM kat_users WHERE email = ?";
    private final static String VERIFY_USER = "UPDATE kat_users SET verified = true WHERE email = ?";
    private final static String UPDATE_USERNAME = "UPDATE kat_users SET username = ? WHERE email = ?";
    private final static String CREATE_USER = "INSERT INTO kat_users (username, email, password) VALUES (?, ?, ?)";
    private final static String SELECT_ALL_USERS = "INSERT INTO kat_users (username, email, password) VALUES (?, ?, ?)";
    private final static String SELECT_USER_BY_EMAIL = "SELECT id, username, password FROM kat_users WHERE email = ?";

    public UserDaoImpl() {
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        User user = null;
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(id, username, email, password);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error finding user by email");
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> userList = new ArrayList<>();
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_USERS);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                userList.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error finding all users");
        }
        return userList;
    }

    @Override
    public void create(User user) throws DaoException {
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CREATE_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            preparedStatement.executeUpdate();
            System.out.println("create");
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error creating user");
        }
    }

    @Override
    public void updateUsername(String email, String username) throws DaoException {
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_USERNAME)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error updating username");
        }
    }

    @Override
    public void verifyUser(String email) throws DaoException {
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(VERIFY_USER)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error verifying user");
        }
    }

    @Override
    public boolean userVerified(String email) throws DaoException {
        boolean userVerified = false;
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_VERIFIED)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userVerified = rs.getBoolean("verified");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error checking user verification");
        }
        return userVerified;
    }

    @Override
    public void delete(String email) throws DaoException {
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error deleting user");
        }
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}