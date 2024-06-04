package com.esde.web.service;

import javax.servlet.http.HttpServletRequest;

public interface SessionService {
    void createSession(HttpServletRequest request, String email);
}