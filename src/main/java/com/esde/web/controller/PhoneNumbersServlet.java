package com.esde.web.controller;

import com.esde.web.model.PhoneNumber;
import com.esde.web.model.User;
import com.esde.web.service.PhoneNumberService;
import com.esde.web.service.UserService;
import com.esde.web.service.impl.PhoneNumberServiceImpl;
import com.esde.web.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/numbers")
public class PhoneNumbersServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();
    private final PhoneNumberService phoneNumberService = new PhoneNumberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET");
        getAllNumbers(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        }

    public void getAllNumbers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String userEmail = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(userEmail);
        List<PhoneNumber> phoneNumbers = phoneNumberService.findAllNumbers(user.getUserId());
        request.getSession().setAttribute("contactsList", phoneNumbers);
        response.sendRedirect(request.getContextPath() + "/pages/contacts.jsp");
        logger.info("Contacts list returned");
    }
}