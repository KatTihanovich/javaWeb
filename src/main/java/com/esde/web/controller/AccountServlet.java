package com.esde.web.controller;

import com.esde.web.model.User;
import com.esde.web.service.UserService;
import com.esde.web.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        switch (path){
            case "/user":
                String userEmail = (String) session.getAttribute("email");
                User user = userService.findUserByEmail(userEmail);
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/pages/account.jsp");
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
        if ("/update".equals(path)){
            handleUpdate(request, response);
        } else if ("/delete".equals(path)) {
            handleDelete(request, response);
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = (String) request.getSession().getAttribute("email");
        if (username != null){
            userService.updateUsername(email, username);
            response.sendRedirect("pages/success.jsp");
        }else {
            response.sendRedirect("pages/updateAccount.jsp");
        }

    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = (String) request.getSession().getAttribute("email");
        userService.deleteUser(email);
        HttpSession session = request.getSession(false);
        session.invalidate();
        response.sendRedirect("pages/register.jsp");
    }

}