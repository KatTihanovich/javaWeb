package com.esde.web.servlet;

import com.esde.web.model.User;
import com.esde.web.service.UserService;
import com.esde.web.service.exception.ServiceException;
import com.esde.web.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/user", "/update", "/delete"})
public class AccountServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET");
        HttpSession session = request.getSession(false);
        String path = request.getServletPath();
        switch (path) {
            case "/user":
                String userEmail = (String) session.getAttribute("email");
                User user = null;
                try {
                    user = userService.findUserByEmail(userEmail);
                } catch (ServiceException e) {
                    logger.error(e);
                    request.setAttribute("error_msg", e.getCause());
                    request.getRequestDispatcher("pages/error/error404.jsp").forward(request, response);
                }
                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("/pages/account.jsp").forward(request, response);
                break;
            case "/update":
                request.getRequestDispatcher("pages/updateAccount.jsp").forward(request, response);
                break;
            case "/delete":
                handleDelete(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST");
        String path = request.getServletPath();
        if ("/update".equals(path)) {
            handleUpdate(request, response);
        } else if ("/delete".equals(path)) {
            handleDelete(request, response);
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String email = (String) request.getSession().getAttribute("email");
        if (username != null) {
            try {
                userService.updateUsername(email, username);
            } catch (ServiceException e) {
                logger.error(e);
                request.setAttribute("error_msg", e.getCause());
                request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
            }
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        cookie.setValue(username);
                        cookie.setMaxAge(60 * 60 * 24);
                        response.addCookie(cookie);
                        break;
                    }
                }
            }
            response.sendRedirect("pages/success.jsp");
        } else {
            response.sendRedirect("pages/updateAccount.jsp");
        }

    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = (String) request.getSession().getAttribute("email");
        try {
            userService.deleteUser(email);
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute("error_msg", e.getCause());
            request.getRequestDispatcher("pages/error/error500.jsp").forward(request, response);
        }
        HttpSession session = request.getSession(false);
        session.invalidate();
        response.sendRedirect("pages/register.jsp");
    }
}