package com.esde.web.dao.impl;

import com.esde.web.dao.PhoneNumberDao;
import com.esde.web.dao.exception.DaoException;
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
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_NUMBERS = "select id, last_name, phone, photo from phone_numbers where userid =?  ORDER BY id LIMIT ? OFFSET ?";
    private static final String SELECT_NUMBER_BY_PHONE = "SELECT last_name, phone, photo FROM phone_numbers WHERE phone = ?";
    private static final String UPDATE_PHONE_NUMBER = "UPDATE phone_numbers SET phone = ? WHERE phone = ? AND userid = ?";
    private static final String UPDATE_PHOTO = "UPDATE phone_numbers SET photo = ? WHERE phone = ? AND userid = ?";
    private static final String UPDATE_PHONE_NAME = "UPDATE phone_numbers SET last_name = ? WHERE phone = ? AND userid = ?";
    private static final String DELETE_NUMBER = "DELETE FROM phone_numbers WHERE phone = ? AND userid = ?";
    private static final String CREATE_NUMBER = "INSERT INTO phone_numbers (last_name, phone, photo, userid) VALUES (?, ?, ?, ?)";
    private static final String SELECT_NUMBER_BY_PHONE_ALL = "SELECT * FROM phone_numbers WHERE userid = ? AND phone = ?";
    private static final String SELECT_NUMBER_OF_CONTACTS = "SELECT COUNT(*) FROM phone_numbers WHERE userid = ?";

    public PhoneNumberDaoImpl() {
    }

    @Override
    public PhoneNumber findByPhoneNumber(String phone) throws DaoException {
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NUMBER_BY_PHONE)) {
            preparedStatement.setString(1, phone);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return extractPhoneFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error finding contact by phone number");
        }
        return null;
    }

    @Override
    public void updateNumber(String oldNumber, String newNumber, int userId) throws DaoException {
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_PHONE_NUMBER)) {
            preparedStatement.setString(1, newNumber);
            preparedStatement.setString(2, oldNumber);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error updating contact number");
        }
    }

    @Override
    public void updatePhoto(String oldNumber, InputStream photo, int userId) throws DaoException {
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_PHOTO)) {
            preparedStatement.setBinaryStream(1, photo);
            preparedStatement.setString(2, oldNumber);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error updating contact photo");
        }
    }

    @Override
    public void updateName(String oldNumber, String newName, int userId) throws DaoException {
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_PHONE_NAME)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, oldNumber);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error updating contact name");
        }
    }

    @Override
    public void delete(String phone, int userId) throws DaoException {
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_NUMBER)) {
            preparedStatement.setString(1, phone);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error deleting contact");
        }
    }

    @Override
    public void create(PhoneNumber phone) throws DaoException {
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CREATE_NUMBER)) {
            preparedStatement.setString(1, phone.getLastname());
            preparedStatement.setString(2, phone.getPhone());
            preparedStatement.setBinaryStream(3, phone.getPhoto());
            preparedStatement.setInt(4, phone.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error creating contact");
        }
    }

    @Override
    public PhoneNumber findByNumber(String phone, int userId) throws DaoException {
        PhoneNumber phoneNumber = null;
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_NUMBER_BY_PHONE_ALL)) {
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
            throw new DaoException("Error finding contact by phone number");
        }
        return phoneNumber;
    }

    @Override
    public List<PhoneNumber> selectAllNumbers(int id, int pageNum, int pageSize) throws IOException, DaoException {
        List<PhoneNumber> numbers = new ArrayList<>();
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_NUMBERS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, (pageNum - 1) * pageSize);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("last_name");
                String number = rs.getString("phone");
                InputStream imageStream = rs.getBinaryStream("photo");
                String base64Image = null;
                if (imageStream != null) {
                    byte[] imageBytes = IOUtils.toByteArray(imageStream);
                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                }
                numbers.add(new PhoneNumber(name, number, base64Image));
            }
            logger.info("SELECT ALL numbers successful");
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error finding all contacts");
        }
        return numbers;
    }

    @Override
    public int findNumberOfContacts(int id) throws DaoException {
        int numOfContacts = 0;
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_NUMBER_OF_CONTACTS)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                numOfContacts = rs.getInt("count");
            }
            logger.info("SELECT number of contacts successful");
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error finding number of contacts");
        }
        return numOfContacts;
    }

    private PhoneNumber extractPhoneFromResultSet(ResultSet rs) throws SQLException {
        PhoneNumber phone = new PhoneNumber();
        phone.setLastname(rs.getString("lastname"));
        phone.setPhone(rs.getString("phone"));
        return phone;
    }
}