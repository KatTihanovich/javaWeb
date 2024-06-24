package com.esde.web.service;

import com.esde.web.service.exception.ServiceException;

public interface EmailService {
    String createCode();

    void sendEmail(String email, String code) throws ServiceException;
}