package com.esde.web.dao;

import com.esde.web.dao.exception.DaoException;
import com.esde.web.model.PhoneNumber;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PhoneNumberDao {
    PhoneNumber findByPhoneNumber(String phone) throws DaoException;

    void updateName(String oldNumber, String newName, int userId) throws DaoException;

    void updatePhoto(String oldNumber, InputStream photo, int userId) throws DaoException;

    void create(PhoneNumber phone) throws DaoException;

    PhoneNumber findByNumber(String phone, int userId) throws DaoException;

    List<PhoneNumber> selectAllNumbers(int id, int pageNum, int pageSize) throws IOException, DaoException;

    void updateNumber(String oldNumber, String newNumber, int userId) throws DaoException;

    void delete(String phone, int userId) throws DaoException;

    int findNumberOfContacts(int id) throws DaoException;
}