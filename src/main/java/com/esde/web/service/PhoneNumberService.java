package com.esde.web.service;

import com.esde.web.model.PhoneNumber;

import java.io.InputStream;
import java.util.List;

public interface PhoneNumberService {

    PhoneNumber findByPhoneNumber(String number);
    void create(PhoneNumber phone);

    void updateNumber(String oldNumber, String newNumber, int userId);

    void updateName(String oldNumber, String newName, int userId);

    void updatePhoto(String oldNumber, InputStream photo, int userId);

    void delete(String phone, int userId);

    List<PhoneNumber> findAllNumbers(int userId);

    boolean existsForThisUser(String phone, int userId);
}