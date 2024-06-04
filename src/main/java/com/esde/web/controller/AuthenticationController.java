package com.esde.web.controller;

import com.esde.web.model.User;
import com.esde.web.service.EmailService;
import com.esde.web.service.SessionService;
import com.esde.web.service.UserService;
import com.esde.web.service.impl.EmailServiceImpl;
import com.esde.web.service.impl.SessionServiceImpl;
import com.esde.web.service.impl.UserServiceImpl;
import com.esde.web.validator.AuthenticationValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthenticationController", urlPatterns = {"/login", "/register", "/logout", "/verify"})
public class AuthenticationController extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    private final SessionService sessionService = new SessionServiceImpl();
    private final EmailService emailService = new EmailServiceImpl();
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET");
        String path = request.getServletPath();
        if ("/login".equals(path)) {
            request.getRequestDispatcher("pages/login.jsp").forward(request, response);
        } else if ("/register".equals(path)) {
            request.getRequestDispatcher("pages/register.jsp").forward(request, response);
        } else if ("/logout".equals(path)) {
            handleLogout(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST");
        String path = request.getServletPath();
        if ("/login".equals(path)) {
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            if (!AuthenticationValidator.validateAllFieldsNotEmpty(email, password)) {
                request.setAttribute("error", "All fields are required");
                request.getRequestDispatcher("pages/login.jsp").forward(request, response);
            } else {
                handleLogin(request, response);
            }

        } else if ("/register".equals(path)) {
            String username = request.getParameter("username").trim();
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            if (!AuthenticationValidator.validateAllFieldsNotEmpty(username, email, password)) {
                request.setAttribute("error", "All fields are required");
                request.getRequestDispatcher("pages/register.jsp").forward(request, response);
            }
            if (!AuthenticationValidator.validateEmailMatchesPattern(email)) {
                request.setAttribute("error", "Invalid email format");
                request.getRequestDispatcher("pages/register.jsp").forward(request, response);
            } else {
                handleRegistration(request, response);
            }
        } else if ("/logout".equals(path)) {
            handleLogout(request, response);
        } else if ("/verify".equals(path)){
            handleVerification(request, response);
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User newUser = new User(username, email, password);
        userService.createUser(newUser);
        request.getSession().setAttribute("currentUser", newUser);
        String code = emailService.createCode();
        emailService.sendEmail(email, code);
        HttpSession session = request.getSession();
        session.setAttribute("code", code);
        response.sendRedirect("pages/verify.jsp");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        if (userService.userVerified(email)) {
            String password = request.getParameter("password");
            User user = userService.login(email, password);
            if (user != null) {
                logger.info("User with email " + email + " logged in");
                sessionService.createSession(request, email);
                response.sendRedirect("pages/success.jsp");
            } else {
                logger.info("Invalid data for login");
                response.sendRedirect("pages/login.jsp");
            }
        } else {
            response.sendRedirect("pages/error/error.jsp");
        }

    }

    public void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        logger.info("User " + session.getAttribute("email") + " logged out");
        session.invalidate();
        response.sendRedirect("pages/home.jsp");
    }

    public void handleVerification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        User user = (User) session.getAttribute("currentUser");
        if (code.equals(session.getAttribute("code"))) {
            userService.verifyUser(user.getEmail());
            response.sendRedirect("pages/home.jsp");
            logger.info("verification successful");
        } else {
            response.sendRedirect("pages/verify.jsp");
            logger.error("verification failed");
        }
    }

}