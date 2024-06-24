package com.esde.web.service.impl;

import com.esde.web.dao.PhoneNumberDao;
import com.esde.web.dao.exception.DaoException;
import com.esde.web.dao.impl.PhoneNumberDaoImpl;
import com.esde.web.model.PhoneNumber;
import com.esde.web.service.PhoneNumberService;
import com.esde.web.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberDao phoneNumberDao = new PhoneNumberDaoImpl();
    private static final Logger logger = LogManager.getLogger();

    public PhoneNumber findByPhoneNumber(String phone) throws ServiceException {
        PhoneNumber number = null;
        try {
            number = phoneNumberDao.findByPhoneNumber(phone);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return number;
    }

    public void create(PhoneNumber phone) throws ServiceException {
        try {
            phoneNumberDao.create(phone);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateNumber(String oldNumber, String newNumber, int userId) throws ServiceException {
        existsForThisUser(oldNumber, userId);
        try {
            phoneNumberDao.updateNumber(oldNumber, newNumber, userId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateName(String oldNumber, String newName, int userId) throws ServiceException {
        existsForThisUser(oldNumber, userId);
        try {
            phoneNumberDao.updateName(oldNumber, newName, userId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updatePhoto(String oldNumber, InputStream photo, int userId) throws ServiceException {
        existsForThisUser(oldNumber, userId);
        try {
            phoneNumberDao.updatePhoto(oldNumber, photo, userId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void delete(String phone, int userId) throws ServiceException {
        existsForThisUser(phone, userId);
        try {
            phoneNumberDao.delete(phone, userId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PhoneNumber> findAllNumbers(int userId, int pageNum, int pageSize) throws ServiceException {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        try {
            phoneNumbers = phoneNumberDao.selectAllNumbers(userId, pageNum, pageSize);
        } catch (IOException | DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return phoneNumbers;
    }

    @Override
    public void existsForThisUser(String phone, int userId) throws ServiceException {
        boolean existsForThisUser = false;
        try {
            existsForThisUser = phoneNumberDao.findByNumber(phone, userId) != null;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        if (!existsForThisUser) {
            throw new ServiceException("There is no such contact");
        }
    }

    @Override
    public int findNumberOfContacts(int userId) throws ServiceException {
        int numberOfContacts = 0;
        try {
            numberOfContacts = phoneNumberDao.findNumberOfContacts(userId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return numberOfContacts;
    }
}