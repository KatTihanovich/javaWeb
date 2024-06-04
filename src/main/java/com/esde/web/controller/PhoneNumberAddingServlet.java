package com.esde.web.controller;

import com.esde.web.dao.PhoneNumberDao;
import com.esde.web.dao.impl.PhoneNumberDaoImpl;
import com.esde.web.model.PhoneNumber;
import com.esde.web.model.User;
import com.esde.web.service.UserService;
import com.esde.web.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/addNumber")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class PhoneNumberAddingServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private final PhoneNumberDao phoneNumberDao = new PhoneNumberDaoImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("GET");
        response.sendRedirect("pages/addingPhoneNumber.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST");
        String userEmail = (String) request.getSession().getAttribute("email");
        Part filePart = request.getPart("image");
        String lastname = request.getParameter("name");
        String phone = request.getParameter("number");
        User user = userService.findUserByEmail(userEmail);

        PhoneNumber phoneNumber = new PhoneNumber(lastname, phone, filePart.getInputStream(), user.getUserId());
        phoneNumberDao.create(phoneNumber);
        response.sendRedirect("pages/success.jsp");
    }

}