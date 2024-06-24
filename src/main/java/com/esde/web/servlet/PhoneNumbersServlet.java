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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    public void getAllNumbers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String userEmail = (String) session.getAttribute("email");
        int pageNum = 1;
        int pageSize = 5;
        if (request.getParameter("page") != null) {
            pageNum = Integer.parseInt(request.getParameter("page"));
        }

        User user = null;
        try {
            user = userService.findUserByEmail(userEmail);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error404.jsp").forward(request, response);
        }

        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        try {
            phoneNumbers = phoneNumberService.findAllNumbers(user.getUserId(), pageNum, pageSize);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error404.jsp").forward(request, response);
        }

        int numOfRecords = 0;
        try {
            numOfRecords = phoneNumberService.findNumberOfContacts(user.getUserId());
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }

        int numOfPages = (int) Math.ceil(numOfRecords * 1.0
                / pageSize);
        request.getSession().setAttribute("contactsList", phoneNumbers);
        request.getSession().setAttribute("currentPage", pageNum);
        request.getSession().setAttribute("numOfPages", numOfPages);
        request.getRequestDispatcher("/pages/contacts.jsp").forward(request, response);
        logger.info("Contacts list returned");
    }
}