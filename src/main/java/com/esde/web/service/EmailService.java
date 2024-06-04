package com.esde.web.service;

public interface EmailService {
    String createCode();

    void sendEmail(String email, String code);
}

