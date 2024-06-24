package com.esde.web.service.impl;

import com.esde.web.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionServiceImpl implements SessionService {
    @Override
    public void createSession(HttpServletRequest request, String email) {
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
    }
}