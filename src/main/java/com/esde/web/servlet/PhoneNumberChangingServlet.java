package com.esde.web.servlet;

import com.esde.web.model.User;
import com.esde.web.service.PhoneNumberService;
import com.esde.web.service.UserService;
import com.esde.web.service.exception.ServiceException;
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
        switch (path) {
            case "/editPhoneNumber":
                request.getRequestDispatcher("pages/phoneNumbersEditing.jsp").forward(request, response);
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

        User user = null;
        try {
            user = userService.findUserByEmail(userEmail);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error404.jsp").forward(request, response);
        }

        int userId = user.getUserId();
        String oldNumber = request.getParameter("oldNumber");
        String path = request.getServletPath();
        switch (path) {
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

    public void updateNumber(HttpServletRequest request, HttpServletResponse response, String oldNumber, int userId) throws IOException, ServletException {
        String newNumber = request.getParameter("newNumber");

        try {
            phoneNumberService.updateNumber(oldNumber, newNumber, userId);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }
        response.sendRedirect("pages/success.jsp");
    }

    public void updateName(HttpServletRequest request, HttpServletResponse response, String oldNumber, int userId) throws IOException, ServletException {
        String newName = request.getParameter("newName");

        try {
            phoneNumberService.updateName(oldNumber, newName, userId);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }
        response.sendRedirect("pages/success.jsp");
    }

    public void updateImage(HttpServletRequest request, HttpServletResponse response, String oldNumber, int userId) throws ServletException, IOException {
        Part filePart = request.getPart("image");

        try {
            phoneNumberService.updatePhoto(oldNumber, filePart.getInputStream(), userId);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }
        response.sendRedirect("pages/success.jsp");
    }

    public void deletePhoneNumber(HttpServletRequest request, HttpServletResponse response, String oldNumber, int userId) throws IOException, ServletException {
        try {
            phoneNumberService.delete(oldNumber, userId);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }
        response.sendRedirect("pages/success.jsp");
    }
}