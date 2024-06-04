package com.esde.web.dao.impl;

import com.esde.web.dao.UserDao;
import com.esde.web.model.User;
import com.esde.web.pool.DatabasePool;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

//    private DataSource dataSource = DatabaseConfig.getDataSource();

    public UserDaoImpl(){
        DatabasePool.initializeDataSource();
    }
    @Override
    public User findByEmail(String email) {
        String sql = "SELECT id, username, password FROM kat_users WHERE email = ?";//константы
        User user = null;
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(id, username, email, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching user by username", e);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT username, email, password FROM kat_users";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                userList.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all users", e);
        }
        return userList;
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO kat_users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            preparedStatement.executeUpdate();
            System.out.println("create");
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    @Override
    public void updateUsername(String email, String username) {
        String sql = "UPDATE kat_users SET username = ? WHERE email = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    @Override
    public void verifyUser(String email) {
        String sql = "UPDATE kat_users SET verified = true WHERE email = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    @Override
    public boolean userVerified(String email) {
        String sql = "SELECT verified FROM kat_users WHERE email = ?";
        boolean userVerified = false;
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userVerified = rs.getBoolean("verified");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error selecting user", e);
        }
        return userVerified;
    }


    @Override
    public void delete(String email) {
        String sql = "DELETE FROM kat_users WHERE email = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;
    }

    private void setPreparedStatementParameters(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getPassword());
    }
}