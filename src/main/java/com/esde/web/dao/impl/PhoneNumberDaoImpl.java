package com.esde.web.dao.impl;

import com.esde.web.dao.PhoneNumberDao;
import com.esde.web.model.PhoneNumber;
import com.esde.web.pool.DatabasePool;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PhoneNumberDaoImpl implements PhoneNumberDao {
//
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL_NUMBERS = "select last_name, phone, photo from phone_numbers where userid =?";

    public PhoneNumberDaoImpl(){
        DatabasePool.initializeDataSource();
    }

    @Override
    public PhoneNumber findByPhoneNumber(String phone) {
        String sql = "SELECT last_name, phone, photo FROM phone_numbers WHERE phone = ?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, phone);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return extractPhoneFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching phone by phone number", e);
        }
        return null;
    }


    @Override
    public void updateNumber(String oldNumber, String newNumber, int userId) {
        String sql = "UPDATE phone_numbers SET phone = ? WHERE phone = ? AND userid = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, newNumber);
            preparedStatement.setString(2, oldNumber);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error: you can't update contact", e);
        }
    }

    @Override
    public void updatePhoto(String oldNumber, InputStream photo, int userId) {
        String sql = "UPDATE phone_numbers SET photo = ? WHERE phone = ? AND userid = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setBinaryStream(1, photo);
            preparedStatement.setString(2, oldNumber);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error: you can't update contact", e);
        }
    }

    @Override
    public void updateName(String oldNumber, String newName, int userId) {
        String sql = "UPDATE phone_numbers SET last_name = ? WHERE phone = ? AND userid = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, oldNumber);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error: you can't update contact", e);
        }
    }

    @Override
    public void delete(String phone, int userId) {
        String sql = "DELETE FROM phone_numbers WHERE phone = ? AND userid = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, phone);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error: you can't delete contact", e);
        }
    }

    @Override
    public void create(PhoneNumber phone) {
        String sql = "INSERT INTO phone_numbers (last_name, phone, photo, userid) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, phone.getLastname());
            preparedStatement.setString(2, phone.getPhone());
            preparedStatement.setBinaryStream(3, phone.getPhoto());
            preparedStatement.setInt(4, phone.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public PhoneNumber findByNumber(String phone, int userId) {
        PhoneNumber phoneNumber = null;
        String sql = "SELECT * FROM phone_numbers WHERE userid = ? AND phone = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, phone);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String lastName = rs.getString("last_name");
                String phone1 = rs.getString("phone");
                int userID = rs.getInt("userid");
                phoneNumber = new PhoneNumber(lastName, phone1, userID);
            }
            logger.info("SELECT phone_number BY phone successful");
        } catch (SQLException e) {
            logger.error(e);
        }
        return phoneNumber;
    }

    @Override
    public List<PhoneNumber> selectAllNumbers(int id) throws IOException {
        List<PhoneNumber> numbers = new ArrayList<>();
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_NUMBERS)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("last_name");
                String number = rs.getString("phone");
                InputStream imageStream = rs.getBinaryStream("photo");
                String base64Image = null;
                if (imageStream!= null){
                    byte[] imageBytes = IOUtils.toByteArray(imageStream);
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                }
                numbers.add(new PhoneNumber(name, number, base64Image));
            }
            logger.info("SELECT ALL numbers successful");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return numbers;
    }

    private PhoneNumber extractPhoneFromResultSet(ResultSet rs) throws SQLException {
        PhoneNumber phone = new PhoneNumber();
        phone.setLastname(rs.getString("lastname"));
        phone.setPhone(rs.getString("phone"));
//        phone.setPhoto(rs.getString("photo_url"));
        return phone;
    }
}