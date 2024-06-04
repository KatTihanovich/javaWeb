package com.esde.web.dao;

import com.esde.web.model.PhoneNumber;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PhoneNumberDao {
    PhoneNumber findByPhoneNumber(String phone);

    void updateName(String oldNumber, String newName, int userId);

    void updatePhoto(String oldNumber, InputStream photo, int userId);

    void create(PhoneNumber phone);

    PhoneNumber findByNumber(String phone, int userId);

    List<PhoneNumber> selectAllNumbers(int id) throws IOException;

    void updateNumber(String oldNumber, String newNumber, int userId);

    void delete(String phone, int userId);
}