package com.esde.web.controller;

import com.esde.web.model.User;
import com.esde.web.service.PhoneNumberService;
import com.esde.web.service.UserService;
import com.esde.web.service.impl.PhoneNumberServiceImpl;
import com.esde.web.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/editPhoneNumber", "/editPhoneName", "/editPhoneImage", "/deleteNumber"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class PhoneNumberChangingServlet extends HttpServlet {
    private final PhoneNumberService phoneNumberService = new PhoneNumberServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET");
        String path = request.getServletPath();
        switch (path){
            case "/editPhoneNumber":
                response.sendRedirect("pages/phoneNumbersEditing.jsp");
                break;
            case "/editPhoneName":
                request.getRequestDispatcher("pages/phoneNumberNameEditing.jsp").forward(request, response);
                break;
            case "/editPhoneImage":
                request.getRequestDispatcher("pages/phoneNumberImageEditing.jsp").forward(request, response);
                break;
            case "/deleteNumber":
                request.getRequestDispatcher("pages/phoneNumberDeleting.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST");
        String userEmail = (String) request.getSession().getAttribute("email");
        User user = userService.findUserByEmail(userEmail);
        int userId = user.getUserId();
        String oldNumber = request.getParameter("oldNumber");
        String path = request.getServletPath();
        switch (path){
            case "/editPhoneNumber":
                updateNumber(request, response, oldNumber, userId);
                break;
            case "/editPhoneName":
                updateName(request, response, oldNumber, userId);
                break;
            case "/editPhoneImage":
                updateImage(request, response, oldNumber, userId);
                break;
            case "/deleteNumber":
                deletePhoneNumber(request, response, oldNumber, userId);
                break;
        }

    }

    public void updateNumber(HttpServletRequest request, HttpServletResponse response, String oldNumber, int userId) throws IOException {
        String newNumber = request.getParameter("newNumber");
        phoneNumberService.updateNumber(oldNumber, newNumber, userId);
        response.sendRedirect("pages/success.jsp");
    }

    public void updateName(HttpServletRequest request, HttpServletResponse response, String oldNumber, int userId) throws IOException {
        String newName = request.getParameter("newName");
        phoneNumberService.updateName(oldNumber, newName, userId);
        response.sendRedirect("pages/success.jsp");
    }

    public void updateImage(HttpServletRequest request, HttpServletResponse response, String oldNumber, int userId) throws ServletException, IOException {
        Part filePart = request.getPart("image");
        phoneNumberService.updatePhoto(oldNumber, filePart.getInputStream(), userId);
        response.sendRedirect("pages/success.jsp");
    }

    public void deletePhoneNumber(HttpServletRequest request, HttpServletResponse response, String oldNumber, int userId) throws IOException {
        phoneNumberService.delete(oldNumber, userId);
        response.sendRedirect("pages/success.jsp");
    }

}