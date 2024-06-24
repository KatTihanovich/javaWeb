package com.esde.web.service;

import com.esde.web.model.PhoneNumber;
import com.esde.web.service.exception.ServiceException;

import java.io.InputStream;
import java.util.List;

public interface PhoneNumberService {

    PhoneNumber findByPhoneNumber(String number) throws ServiceException;

    void create(PhoneNumber phone) throws ServiceException;

    void updateNumber(String oldNumber, String newNumber, int userId) throws ServiceException;

    void updateName(String oldNumber, String newName, int userId) throws ServiceException;

    void updatePhoto(String oldNumber, InputStream photo, int userId) throws ServiceException;

    void delete(String phone, int userId) throws ServiceException;

    List<PhoneNumber> findAllNumbers(int userId, int pageNum, int pageSize) throws ServiceException;

    void existsForThisUser(String phone, int userId) throws ServiceException;

    int findNumberOfContacts(int userId) throws ServiceException;
}