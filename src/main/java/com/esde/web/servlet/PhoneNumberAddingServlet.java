package com.esde.web.servlet;

import com.esde.web.model.PhoneNumber;
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

@WebServlet(urlPatterns = "/addNumber")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class PhoneNumberAddingServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();
    private final PhoneNumberService numberService = new PhoneNumberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("GET");
        request.getRequestDispatcher("pages/addingPhoneNumber.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST");
        String userEmail = (String) request.getSession().getAttribute("email");
        Part filePart = request.getPart("image");
        String lastname = request.getParameter("name");
        String phone = request.getParameter("number");

        User user = null;
        try {
            user = userService.findUserByEmail(userEmail);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error404.jsp").forward(request, response);
        }

        PhoneNumber phoneNumber = new PhoneNumber(lastname, phone, filePart.getInputStream(), user.getUserId());

        try {
            numberService.create(phoneNumber);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }
        response.sendRedirect("pages/success.jsp");
    }
}