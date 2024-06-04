package com.esde.web.service.impl;

import com.esde.web.dao.PhoneNumberDao;
import com.esde.web.dao.impl.PhoneNumberDaoImpl;
import com.esde.web.model.PhoneNumber;
import com.esde.web.service.PhoneNumberService;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberDao phoneNumberDao = new PhoneNumberDaoImpl();

    public PhoneNumber findByPhoneNumber(String phone) {
        return phoneNumberDao.findByPhoneNumber(phone);
    }

    public void create(PhoneNumber phone) {
        phoneNumberDao.create(phone);
    }

    @Override
    public void updateNumber(String oldNumber, String newNumber, int userId) {
        if (!existsForThisUser(oldNumber, userId)){
            throw new RuntimeException("no such contact");
        }
        phoneNumberDao.updateNumber(oldNumber, newNumber, userId);
    }

    @Override
    public void updateName(String oldNumber, String newName, int userId) {
        if (!existsForThisUser(oldNumber, userId)){
            throw new RuntimeException("no such contact");
        }
        phoneNumberDao.updateName(oldNumber, newName, userId);
    }

    @Override
    public void updatePhoto(String oldNumber, InputStream photo, int userId) {
        if (!existsForThisUser(oldNumber, userId)){
            throw new RuntimeException("no such contact");
        }
        phoneNumberDao.updatePhoto(oldNumber, photo, userId);
    }

    public void delete(String phone, int userId) {
        if (!existsForThisUser(phone, userId)){
            throw new RuntimeException("no such contact");
        }
        phoneNumberDao.delete(phone, userId);
    }

    @Override
    public List<PhoneNumber> findAllNumbers(int userId){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        try {
            phoneNumbers = phoneNumberDao.selectAllNumbers(userId);
        } catch (IOException e){
            e.printStackTrace();
        }
        return phoneNumbers;
    }

    @Override
    public boolean existsForThisUser(String phone, int userId){
       return phoneNumberDao.findByNumber(phone, userId)!=null;
    }
}